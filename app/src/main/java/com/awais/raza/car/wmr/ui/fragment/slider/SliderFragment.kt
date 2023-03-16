package com.awais.raza.car.wmr.ui.fragment.slider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.awais.raza.car.warm.R
import com.awais.raza.car.wmr.base.fragment.BaseFragment
import com.awais.raza.car.warm.databinding.FragmentSliderBinding
import com.awais.raza.car.wmr.ui.fragment.slider.adapter.ViewPagerAdapter
import com.awais.raza.car.wmr.utils.gone
import com.awais.raza.car.wmr.utils.show
import com.google.android.material.tabs.TabLayoutMediator

class SliderFragment : BaseFragment(R.layout.fragment_slider), View.OnClickListener {

    private var _binding: FragmentSliderBinding? = null
    private val binding
        get() = _binding!!
    private val args by navArgs<SliderFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSliderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setObservers()
        setOnClickListener()
    }


    override fun initialize() {
        setOnBoardingViewPager()
    }

    override fun setObservers() {
    }

    override fun setOnClickListener() {
        binding.viewBack.setOnClickListener(this)
        binding.viewForward.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            binding.viewBack.id -> {
                var tab: Int = binding.viewPager.currentItem
                if (tab > 0) {
                    tab--
                    binding.viewPager.currentItem = tab
                } else if (tab == 0) {
                    binding.viewPager.currentItem = 0
                }
            }
            binding.viewForward.id -> {
                var tab: Int = binding.viewPager.currentItem
                if (tab == 3) {
                    if(args.destination == "splash"){
                        findNavController().navigate(R.id.action_sliderFragment_to_permissionFragment)
                    }else{
                        findNavController().popBackStack()
                    }

                } else {
                    tab++
                    binding.viewPager.currentItem = tab
                }

            }
        }
    }

    private fun setOnBoardingViewPager() {
        binding.viewPager.adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabLayout, binding.viewPager)
        { _, _ -> }.attach()

        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (position == 0) {
                    binding.carViewBtnGroup.gone()
                } else {
                    binding.carViewBtnGroup.show()
                }
            }

        })

        if (binding.viewPager.currentItem == 0) {
            binding.carViewBtnGroup.gone()
        } else {
            binding.carViewBtnGroup.show()
        }
    }

}