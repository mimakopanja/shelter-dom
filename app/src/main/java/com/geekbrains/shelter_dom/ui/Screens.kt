package com.geekbrains.shelter_dom.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.geekbrains.shelter_dom.ui.fragments.AboutFragment
import com.geekbrains.shelter_dom.ui.fragments.HelpShelterFragment
import com.geekbrains.shelter_dom.ui.fragments.MainFragment
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
}