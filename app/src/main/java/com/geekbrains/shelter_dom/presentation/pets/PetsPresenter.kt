package com.geekbrains.shelter_dom.presentation.pets


import android.app.Activity
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.geekbrains.shelter_dom.App
import com.geekbrains.shelter_dom.IMG_BASE_URL
import com.geekbrains.shelter_dom.MainActivity
import com.geekbrains.shelter_dom.PET_DETAIL_TAG
import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.data.pet.repo.PetRepository
import com.geekbrains.shelter_dom.presentation.list.IPetsListPresenter
import com.geekbrains.shelter_dom.presentation.list.PetsView
import com.geekbrains.shelter_dom.ui.DialogPopup
import com.geekbrains.shelter_dom.ui.fragments.MainFragment
import com.geekbrains.shelter_dom.ui.fragments.OurPetsFragment
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import java.util.*
import kotlin.collections.ArrayList

class PetsPresenter(
    private val petsRepo: PetRepository,
    private val router: Router,
    private val uiScheduler: Scheduler
) : MvpPresenter<PetsView>() {

    val mainFragment = MainFragment()
    val petListPresenter = PetsListPresenter()

    class PetsListPresenter : IPetsListPresenter {

        val pets = mutableListOf<Data>()
        private val fullPets = mutableListOf<Data>()

        override fun setFilterPetsBySubstring(substring: String) {

            val filteredList: MutableList<Data> = ArrayList()
            if (substring.isEmpty()) {
                filteredList.addAll(fullPets)
            } else {
                val filterPattern = substring.lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in fullPets) {
                    if (item.name?.lowercase(Locale.getDefault())?.contains(filterPattern) == true) {
                        filteredList.add(item)
                    }
                }
            }
            setFilterPets(filteredList)
        }

        private fun setFilterPets(filteredList: MutableList<Data>) {
            pets.clear()
            pets.addAll(filteredList)
        }

        override var itemClickListener: ((PetItemView) -> Unit)? = null

        override fun bindView(view: PetItemView) {
            pets[view.pos].let { pet ->
                view.loadPet(pet)
            }
        }

        override fun getCount() = pets.size

        fun setPets(list: List<Data>) {
            pets.clear()
            pets.addAll(list)
            fullPets.addAll(list)
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        startLoading()
        viewState.init()

        petListPresenter.itemClickListener = { itemView ->
            val pet = petListPresenter.pets[itemView.pos]
            val intent = Intent(App.INSTANCE.applicationContext, DialogPopup::class.java)
            intent.putExtra(PET_DETAIL_TAG, pet)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            App.INSTANCE.applicationContext.startActivity(intent)
        }

    }

    private fun startLoading() {
        petsRepo.getPets()
            .observeOn(uiScheduler)
            .subscribe({ pets ->
                pets.data?.let { petListPresenter.setPets(it) }
                viewState.updateList()
            }, { error_loading -> viewState.showError(error_loading) })
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}

