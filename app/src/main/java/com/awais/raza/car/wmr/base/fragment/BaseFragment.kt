package com.awais.raza.car.wmr.base.fragment

import android.widget.Toast
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
abstract class BaseFragment(layout: Int) : Fragment(layout) {

    protected abstract fun setOnClickListener()

    protected abstract fun initialize()

    protected abstract fun setObservers()

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}