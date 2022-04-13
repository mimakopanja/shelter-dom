package com.geekbrains.shelter_dom.ui

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.geekbrains.shelter_dom.ui.fragments.*
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    class OpenHomeFragment: FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return MainFragment.newInstance()
        }
    }

    class OpenAboutFragment: FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return AboutFragment.newInstance()
        }
    }

    class OpenHelpShelterFragment: FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return HelpShelterFragment.newInstance()
        }
    }

    class OpenOurPetsFragment: FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return OurPetsFragment.newInstance()
        }
    }

    class OpenContactsFragment: FragmentScreen{
        override fun createFragment(factory: FragmentFactory): Fragment {
            return ContactsFragment.newInstance()
        }
    }
}