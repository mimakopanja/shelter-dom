package com.geekbrains.shelter_dom.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.TextView
import com.geekbrains.shelter_dom.App
import com.geekbrains.shelter_dom.MY_ERROR
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.data.api.PetsApiFactory
import com.geekbrains.shelter_dom.data.pet.repo.PetRepositoryImpl
import com.geekbrains.shelter_dom.databinding.FragmentOurPetsBinding
import com.geekbrains.shelter_dom.presentation.list.PetsView
import com.geekbrains.shelter_dom.presentation.pets.PetsPresenter
import com.geekbrains.shelter_dom.presentation.pets.adapter.PetsAdapter
import com.geekbrains.shelter_dom.ui.FilterDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class OurPetsFragment : MvpAppCompatFragment(), PetsView {

    companion object {
        fun newInstance() = OurPetsFragment()
    }

    private lateinit var binding: FragmentOurPetsBinding
    private var petId: Int = -1

    private val presenter by moxyPresenter {
        PetsPresenter(
            PetRepositoryImpl(PetsApiFactory.api),
            App.INSTANCE.router,
            AndroidSchedulers.mainThread()
        )
    }


    private var adapter: PetsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOurPetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun init() {
        adapter = PetsAdapter(presenter.petListPresenter)
        binding.rvPets.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun showError(message: Throwable) {
        Log.e(MY_ERROR, message.message ?: message.stackTraceToString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.search_menu, menu)
        var searchView = menu.findItem(R.id.action_search)
            .actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        val id: Int = searchView.context.resources
            .getIdentifier("android:id/search_src_text", null, null)
        val textView = searchView.findViewById<View>(id) as TextView
        textView.setTextColor(Color.WHITE)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                adapter?.getFilter()?.filter(s)
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter -> {
                FilterDialog.newInstance().show(requireActivity().supportFragmentManager, "filterDialog")
            }
        }
        return super.onOptionsItemSelected(item)
    }

}