package com.awais.raza.car.wmr.ui.fragment.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.awais.raza.car.warm.R
import com.awais.raza.car.wmr.base.fragment.BaseFragment
import com.awais.raza.car.warm.databinding.FragmentSplashBinding
import com.awais.raza.car.wmr.utils.isIntroductionFinish
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private var _binding: FragmentSplashBinding? = null
    private val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setObservers()
        setOnClickListener()
        setOnBackPressedListener()
    }

    override fun initialize() {
        lifecycleScope.launch {
            delay(1500)
            if (!requireContext().isIntroductionFinish()) {
                findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToSliderFragment("splash")
                    )
            } else {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToChatListingFragment()
                )
            }


        }
    }

    override fun setObservers() {
    }

    override fun setOnClickListener() {
    }

    private fun setOnBackPressedListener() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                }
            })
    }
}