package com.geekbrains.shelter_dom.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekbrains.shelter_dom.utils.App
import com.geekbrains.shelter_dom.utils.MY_ERROR
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.data.api.PetsApiFactory
import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.data.pet.repo.PetRepositoryImpl
import com.geekbrains.shelter_dom.databinding.FragmentOurPetsBinding
import com.geekbrains.shelter_dom.presentation.list.PetsView
import com.geekbrains.shelter_dom.presentation.pets.PetsPresenter
import com.geekbrains.shelter_dom.presentation.pets.adapter.EndlessRecyclerViewScrollListener
import com.geekbrains.shelter_dom.presentation.pets.adapter.PaginationScrollListener
import com.geekbrains.shelter_dom.presentation.pets.adapter.PetsAdapte
import com.geekbrains.shelter_dom.ui.FilterDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.geekbrains.shelter_dom.ui.DialogPopup
import com.geekbrains.shelter_dom.utils.InternetUtils
import com.geekbrains.shelter_dom.utils.PET_DETAIL_TAG
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


@Suppress("DEPRECATION")
class OurPetsFragment : MvpAppCompatFragment(), PetsView {

    companion object {
        fun newInstance() = OurPetsFragment()
    }


    private var adapter: PetsAdapter? = null
    private lateinit var binding: FragmentOurPetsBinding
    lateinit var scrollListener: EndlessRecyclerViewScrollListener

    private val presenter by moxyPresenter {
        PetsPresenter(
            PetRepositoryImpl(PetsApiFactory.api),
            App.INSTANCE.router,
            AndroidSchedulers.mainThread()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOurPetsBinding.inflate(inflater, container, false)
//        binding.animationViewIcon.setAnimation(R.raw.wifi)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun init() {
        val gridLayoutManager = GridLayoutManager(context, 2)
        scrollListener = EndlessRecyclerViewScrollListener(
            (presenter::startLoading),
            gridLayoutManager
        )
        binding.rvPets.addOnScrollListener(scrollListener)
        adapter = PetsAdapter(presenter.petListPresenter)
        binding.rvPets.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun showProgress() {
        binding.progressBarLayout.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBarLayout.visibility = View.GONE
    }

    override fun showInternetConnection() {
        binding.connectionFrame.visibility = View.VISIBLE
    }

    override fun showError(message: Throwable) {
        Log.e(MY_ERROR, message.message ?: message.stackTraceToString())
    }

    override fun scrollList(currentItem: Int) {
        binding.rvPets.layoutManager?.scrollToPosition(currentItem)
    }

    override fun openPetDetails(pet: Data) {
        val intent = Intent(App.INSTANCE.applicationContext, DialogPopup::class.java)
        intent.putExtra(PET_DETAIL_TAG, pet)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        App.INSTANCE.applicationContext.startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.search_menu, menu)
        val searchView = menu.findItem(R.id.action_search)
            .actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE;
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