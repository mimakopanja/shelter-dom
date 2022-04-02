package com.geekbrains.shelter_dom.presentation.pets

import com.geekbrains.shelter_dom.data.pet.Base
import com.geekbrains.shelter_dom.data.pet.Data
import com.geekbrains.shelter_dom.data.pet.PetRepository
import com.geekbrains.shelter_dom.presentation.pets.adapter.PetsAdapter
import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

@InjectViewState
class PetsPresenter(
    private val petsRepo: PetRepository,
    private val router: Router)
    : MvpPresenter<PetsView>() {

    var data: MutableList<Data> = arrayListOf()

    val petClickListener = PetsAdapter.OnClickListener {
//        router.navigateTo(Screens.OpenPetDetailsFragment)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
    }

    fun loadData() {
        petsRepo.getPets(callback)
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    private val callback = object : Callback<Base> {
        override fun onResponse(call: Call<Base>, response: Response<Base>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    data = ArrayList(it.data)
                }
                viewState.updateList()
            }
        }

        override fun onFailure(call: Call<Base>, t: Throwable) {
            viewState.showToast(t.localizedMessage)
        }
    }
}