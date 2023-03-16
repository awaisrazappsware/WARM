package com.awais.raza.car.wmr.ui.fragment.slider.slides

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.awais.raza.car.warm.R
import com.awais.raza.car.wmr.base.fragment.BaseFragment
import com.awais.raza.car.warm.databinding.FragmentFirstSlideBinding


class FirstSlideFragment  : BaseFragment(R.layout.fragment_first_slide),View.OnClickListener {

    private var _binding: FragmentFirstSlideBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstSlideBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setObservers()
        setOnClickListener()
    }

    override fun setOnClickListener() {
        binding.acceptBtn.setOnClickListener(this)
    }

    override fun initialize() {
    }

    override fun setObservers() {
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.acceptBtn.id->{

            }
        }
    }
}