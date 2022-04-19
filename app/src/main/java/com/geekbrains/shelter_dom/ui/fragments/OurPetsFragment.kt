package com.geekbrains.shelter_dom.ui.fragments

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.data.api.PetsApiFactory
import com.geekbrains.shelter_dom.data.pet.model.Data
import com.geekbrains.shelter_dom.data.pet.repo.FilterRepositoryImpl
import com.geekbrains.shelter_dom.data.pet.repo.PetRepositoryImpl
import com.geekbrains.shelter_dom.databinding.BottomSheetFilterDialogBinding
import com.geekbrains.shelter_dom.databinding.FragmentOurPetsBinding
import com.geekbrains.shelter_dom.presentation.filter.age.adapter.AgeAdapter
import com.geekbrains.shelter_dom.presentation.filter.breeds.adapter.BreedAdapter
import com.geekbrains.shelter_dom.presentation.filter.types.adapter.TypeAdapter
import com.geekbrains.shelter_dom.presentation.list.PetsView
import com.geekbrains.shelter_dom.presentation.pets.PetsPresenter
import com.geekbrains.shelter_dom.presentation.pets.adapter.PetsAdapter
import com.geekbrains.shelter_dom.ui.DialogPopup
import com.geekbrains.shelter_dom.utils.App
import com.geekbrains.shelter_dom.utils.PET_DETAIL_TAG
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


@Suppress("DEPRECATION")
class OurPetsFragment : MvpAppCompatFragment(), PetsView {

    companion object {
        fun newInstance() = OurPetsFragment()
    }

    private var adapter: PetsAdapter? = null
    private var typeAdapter: TypeAdapter? = null
    private var breedAdapter: BreedAdapter? = null
    private var ageAdapter: AgeAdapter? = null
    var loading = false
    private lateinit var layoutManager: GridLayoutManager

    private lateinit var binding: FragmentOurPetsBinding
    private lateinit var filterBinding: BottomSheetFilterDialogBinding

    lateinit var filterDialog: BottomSheetDialog

    private val presenter by moxyPresenter {
        PetsPresenter(
            PetRepositoryImpl(PetsApiFactory.api),
            FilterRepositoryImpl(PetsApiFactory.api),
            App.INSTANCE.router,
            AndroidSchedulers.mainThread()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFilterDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOurPetsBinding.inflate(inflater, container, false)
        binding.animationViewIcon.setAnimation(R.raw.wifi)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun init() {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = GridLayoutManager(context, 2)
            binding.rvPets.layoutManager = layoutManager
        } else {
            layoutManager = GridLayoutManager(context, 3)
            binding.rvPets.layoutManager = layoutManager
        }

        binding.rvPets.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemCount + firstVisibleItemPosition >=
                    totalItemCount && firstVisibleItemPosition >= 0
                ) {
                    presenter.nextPage()
                }
            }
        })
        adapter = PetsAdapter(presenter.petListPresenter)
        binding.rvPets.adapter = adapter

        filterBinding.rvTypeFilter.layoutManager = LinearLayoutManager(context)
        typeAdapter = TypeAdapter(presenter.typeListPresenter)
        filterBinding.rvTypeFilter.adapter = typeAdapter

        breedAdapter = BreedAdapter(presenter.breedListPresenter)
        filterBinding.rvBreedFilter.adapter = breedAdapter

        ageAdapter = AgeAdapter(presenter.ageListPresenter)
        filterBinding.rvAgeFilter.adapter = ageAdapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun updateTypes() {
        typeAdapter?.notifyDataSetChanged()
    }

    override fun updateBreeds() {
        breedAdapter?.notifyDataSetChanged()
    }

    override fun updateAges() {
        ageAdapter?.notifyDataSetChanged()
    }

    override fun showProgress() {
        binding.progressBarLayout.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBarLayout.visibility = View.GONE
    }

    override fun noConnection() {
        binding.connectionLayout.visibility = View.VISIBLE
    }

    override fun showSnack(message: String) {
        showSnack(message)
    }

    override fun showError(message: String) {
        showSnack(message.toString())
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
                filterDialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupFilterDialog() {
        filterDialog = BottomSheetDialog(requireContext())
        filterBinding = BottomSheetFilterDialogBinding.inflate(layoutInflater)
        filterDialog.setContentView(filterBinding.root)
        val behavior = filterDialog.behavior
        behavior.peekHeight = (requireActivity().resources.displayMetrics.heightPixels)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        with(filterBinding) {
            llBreedFilter.setOnClickListener {
                if (rvBreedFilter.visibility == View.GONE) {
                    rvBreedFilter.visibility = View.VISIBLE
                    arrowBreed.setImageResource(R.drawable.ic_arrow_up)
                } else {
                    rvBreedFilter.visibility = View.GONE
                    arrowBreed.setImageResource(R.drawable.ic_arrow_down)
                }
            }

            llTypeFilter.setOnClickListener {
                if (rvTypeFilter.visibility == View.GONE) {
                    rvTypeFilter.visibility = View.VISIBLE
                    arrowType.setImageResource(R.drawable.ic_arrow_up)
                } else {
                    rvTypeFilter.visibility = View.GONE
                    arrowType.setImageResource(R.drawable.ic_arrow_down)
                }
            }

            llAgeFilter.setOnClickListener {
                if (rvAgeFilter.visibility == View.GONE) {
                    rvAgeFilter.visibility = View.VISIBLE
                    arrowAge.setImageResource(R.drawable.ic_arrow_up)
                } else {
                    rvAgeFilter.visibility = View.GONE
                    arrowAge.setImageResource(R.drawable.ic_arrow_down)
                }
            }

            llParasitesFilter.setOnClickListener {
                if (llYesOrNo.visibility == View.GONE) {
                    llYesOrNo.visibility = View.VISIBLE
                    arrowParasites.setImageResource(R.drawable.ic_arrow_up)
                } else {
                    llYesOrNo.visibility = View.GONE
                    arrowParasites.setImageResource(R.drawable.ic_arrow_down)
                }
            }

            rgParasites.setOnCheckedChangeListener { radioGroup, i ->
                if (rbYes.isChecked) {
                    presenter.setParasites(rbYes.text as String)
                } else {
                    presenter.setParasites(rbNo.text as String)
                }
            }

            tvReset.setOnClickListener {
                resetRvFilter(rvTypeFilter)
                resetRvFilter(rvBreedFilter)
                resetRvFilter(rvAgeFilter)
                presenter.resetFilter()
            }

            tvReady.setOnClickListener {
                presenter.loadFilteredPets()
                behavior.isHideable = true
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN)
            }
        }
    }

    private fun resetRvFilter(rv: RecyclerView) {
        for (i in 0..rv.childCount) {
            val holder = rv.findViewHolderForAdapterPosition(i)
            holder?.itemView?.findViewById<CheckBox>(R.id.cbItemFilter)?.isChecked = false
        }
        filterBinding.rgParasites.clearCheck()
    }
}