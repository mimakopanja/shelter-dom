package com.geekbrains.shelter_dom.ui.fragments

import android.os.Bundle
import android.view.*
import com.bumptech.glide.Glide
import com.geekbrains.shelter_dom.R
import com.geekbrains.shelter_dom.data.api.PetsApiFactory
import com.geekbrains.shelter_dom.data.model.auth.User
import com.geekbrains.shelter_dom.data.model.user.UserSingle
import com.geekbrains.shelter_dom.data.repo.users.UserRepositoryImpl
import com.geekbrains.shelter_dom.databinding.FragmentUserInfoBinding
import com.geekbrains.shelter_dom.presentation.user.UserPresenter
import com.geekbrains.shelter_dom.presentation.user.UserView
import com.geekbrains.shelter_dom.ui.Screens
import com.geekbrains.shelter_dom.utils.*
import com.shashank.sony.fancytoastlib.FancyToast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class UserFragment : MvpAppCompatFragment(), UserView {

    private lateinit var binding: FragmentUserInfoBinding

    private val perRepo = UserRepositoryImpl(PetsApiFactory.api)

    private var dataSharedPref: User = User()
    private var userData = UserSingle()

    fun newInstance(user: User): UserFragment {
        return UserFragment().apply {
            arguments = Bundle().apply {
                putParcelable(USER_SHARED_PREF_TAG, user)
            }
        }
    }

    private val presenter by moxyPresenter {
        UserPresenter(
            UserRepositoryImpl(PetsApiFactory.api),
            App.INSTANCE.router,
            AndroidSchedulers.mainThread()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun loadUsers(user: UserSingle) {
        fillData(user)
    }

    override fun hideProgress() {
        setVisibility(binding.progressBarLayout, false)
        setVisibility(binding.contentLayout, true)
    }

    private fun fillData(user: UserSingle) {
        val profile = user.data?.profile
        binding.userInfoName.text = user.data?.name
        binding.userPhoneNumber.text = profile?.phone
        binding.userBirthday.text = profile?.birthday_at
        binding.userAddress.text = profile?.address
        binding.userAboutMe.text = profile?.description
        binding.tvUserFullName.text = profile?.name.plus(" ").plus(profile?.surname)
        binding.headerEmail.text = user.data?.email

        Glide.with(requireContext())
            .load(IMG_BASE_URL_AVATAR.plus(user.data?.profile?.avatar))
            .placeholder(R.drawable.ic_account)
            .circleCrop()
            .into(binding.ivUser)
    }

    override fun showError(msg: String) {
        customToast(App.INSTANCE.applicationContext, "User Error!", FancyToast.ERROR)
    }

    override fun showProgress() {
        setVisibility(binding.progressBarLayout, true)
        setVisibility(binding.contentLayout, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataSharedPref = arguments?.getParcelable(USER_SHARED_PREF_TAG) ?: User()
        presenter.getUsers(dataSharedPref.id!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.user_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_user_update -> {
                App.INSTANCE.router.navigateTo(Screens.OpenUserUpdateFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
