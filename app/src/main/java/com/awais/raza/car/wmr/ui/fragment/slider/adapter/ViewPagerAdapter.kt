package com.awais.raza.car.wmr.ui.fragment.slider.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.awais.raza.car.wmr.ui.fragment.slider.slides.FirstSlideFragment
import com.awais.raza.car.wmr.ui.fragment.slider.slides.FourthSlideFragment
import com.awais.raza.car.wmr.ui.fragment.slider.slides.SecondSlideFragment
import com.awais.raza.car.wmr.ui.fragment.slider.slides.ThirdSlideFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                FirstSlideFragment()
            }
            1 -> {
                SecondSlideFragment()
            }
            2 -> {
                ThirdSlideFragment()
            }
            3 -> {
                FourthSlideFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}