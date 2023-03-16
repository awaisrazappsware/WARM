package com.awais.raza.car.wmr.ui.fragment.premium

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.awais.raza.car.warm.R
import com.awais.raza.car.warm.databinding.FragmentPremiumBinding
import com.awais.raza.car.wmr.base.adapter.GenericListAdapter
import com.awais.raza.car.wmr.base.adapter.OnItemClickListener
import com.awais.raza.car.wmr.base.adapter.ViewType
import com.awais.raza.car.wmr.base.fragment.BaseFragment
import com.awais.raza.car.wmr.model.PremiumPackageData
import com.awais.raza.car.wmr.ui.fragment.premium.viewType.OnPremiumItemSelected
import com.awais.raza.car.wmr.ui.fragment.premium.viewType.PremiumListViewType
import com.awais.raza.car.wmr.utils.Constant
import com.awais.raza.car.wmr.utils.PaginationScrollListener
import com.awais.raza.car.wmr.utils.show
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PremiumFragment : BaseFragment(R.layout.fragment_premium), View.OnClickListener {

    private var _binding: FragmentPremiumBinding? = null
    private val binding
        get() = _binding!!
    private var viewTypeArray = ArrayList<ViewType<*>>()
    private var premiumPackageData: PremiumPackageData? = null


    private val adapter by lazy {
        GenericListAdapter(object : OnItemClickListener<ViewType<*>> {
            override fun onItemClicked(view: View, item: ViewType<*>, position: Int) {
                item.data().let { data ->
                    (data as PremiumPackageData).also {
                        premiumPackageData = data
                        data.isChecked = true
                    }
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPremiumBinding.inflate(inflater)
        //binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
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
        setRecyclerView()
        lifecycleScope.launch {
            delay(3000)
            binding.imageViewCancel.show()
        }
    }

    override fun setObservers() {
        val premiumListData = Constant.premiumList
        for (data in premiumListData) {
            viewTypeArray.add(PremiumListViewType(data, requireContext(), object :
                OnPremiumItemSelected {
                @SuppressLint("NotifyDataSetChanged")
                override fun onPremiumItemSelected(position: Int, isChecked: Boolean) {
                    for (checked in premiumListData) {
                        checked.isChecked = false
                    }
                    premiumListData[position].isChecked = true
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                }
            }))
        }
        adapter.items = viewTypeArray
    }

    override fun setOnClickListener() {
        binding.imageViewCancel.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        premiumPackageData = null
    }

    private fun setRecyclerView() {
        binding.recyclerView.adapter = adapter

        val linearLayoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
        binding.recyclerView.addOnScrollListener(object :
            PaginationScrollListener(linearLayoutManager) {
            override fun onScrolled(dy: Int) {
            }

            override fun loadMoreItems() {
            }
        })
    }

    private fun setOnBackPressedListener() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                }
            })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.imageViewCancel.id -> {
                findNavController().popBackStack()
            }
        }
    }
}