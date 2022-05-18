package com.geekbrains.shelter_dom.ui

import android.content.Context
import android.content.Intent
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.geekbrains.shelter_dom.MainActivity
import com.geekbrains.shelter_dom.data.model.auth.User
import com.geekbrains.shelter_dom.data.model.pet.Data
import com.geekbrains.shelter_dom.data.model.user.Profile
import com.geekbrains.shelter_dom.ui.dialogs.ImageDialog
import com.geekbrains.shelter_dom.ui.fragments.*
import com.geekbrains.shelter_dom.ui.fragments.OurPetsFragment
import com.geekbrains.shelter_dom.utils.PET_DETAIL_TAG
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    class OpenMainActivity: ActivityScreen {
        override fun createIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }

    }
    class OpenHomeFragment : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return MainFragment.newInstance()
        }
    }

    class OpenAboutFragment : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return AboutFragment.newInstance()
        }
    }

    class OpenHelpShelterFragment : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return HelpShelterFragment.newInstance()
        }
    }

    class OpenOurPetsFragment : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return OurPetsFragment.newInstance()
        }
    }

    class OpenContactsFragment : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return ContactsFragment.newInstance()
        }
    }

    class OpenDetails(pet: Data) : ActivityScreen {
        private val petBundle = pet
        override fun createIntent(context: Context): Intent {
            val intent = Intent(context, DialogPopup::class.java)
            intent.putExtra(PET_DETAIL_TAG, petBundle)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }

    }

    class OpenImageSlider(pet: Data) : FragmentScreen {
        private val petBundle = pet
        override fun createFragment(factory: FragmentFactory): DialogFragment {
            return ImageDialog.newInstance(petBundle)
        }
    }

    class OpenAuthFragment : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return AuthFragment.newInstance()
        }
    }

    class OpenUserUpdateFragment(user: Profile?) : FragmentScreen {
        private val userInfo = user
        override fun createFragment(factory: FragmentFactory): Fragment {
            return UserUpdateFragment.newInstance(userInfo)
        }
    }

    class OpenUserInfoFragment(user: User) : FragmentScreen {
        private val userBundle = user
        override fun createFragment(factory: FragmentFactory): UserFragment {
            return UserFragment().newInstance(userBundle)
        }
    }

    class OpenFavFragment(token: String) : FragmentScreen {
        private val tokenBundle = token
        override fun createFragment(factory: FragmentFactory): Fragment {
            return FavoriteFragment.newInstance(tokenBundle)
        }
    }
}