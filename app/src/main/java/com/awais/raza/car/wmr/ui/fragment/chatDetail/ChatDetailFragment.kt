package com.awais.raza.car.wmr.ui.fragment.chatDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.awais.raza.car.warm.R
import com.awais.raza.car.wmr.base.adapter.GenericListAdapter
import com.awais.raza.car.wmr.base.adapter.OnItemClickListener
import com.awais.raza.car.wmr.base.adapter.ViewType
import com.awais.raza.car.wmr.base.fragment.BaseFragment
import com.awais.raza.car.warm.databinding.FragmentChatDetailBinding
import com.awais.raza.car.wmr.ui.fragment.chatDetail.viewType.ChatIncomeViewType
import com.awais.raza.car.wmr.ui.fragment.chatDetail.viewType.ChatOutComingViewType
import com.awais.raza.car.wmr.utils.PaginationScrollListener
import com.awais.raza.car.wmr.viewModel.ChatViewModel


class ChatDetailFragment : BaseFragment(R.layout.fragment_chat_detail), View.OnClickListener {
    private var _binding: FragmentChatDetailBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: ChatViewModel by viewModels()
    private val viewTypeArray = ArrayList<ViewType<*>>()
    private val adapter by lazy {
        GenericListAdapter(object : OnItemClickListener<ViewType<*>> {
            override fun onItemClicked(view: View, item: ViewType<*>, position: Int) {
                item.data()?.let {
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatDetailBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setObservers()
        setOnClickListener()
    }

    override fun initialize() {
        setRecyclerView()
    }

    override fun setObservers() {
        viewModel.chatUserResponse.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                viewTypeArray.clear()
                for (chat in data) {
                    binding.textViewTitle.text = chat.user
                    if (chat.user == "You") {
                        viewTypeArray.add(ChatOutComingViewType(chat))
                    } else {
                        viewTypeArray.add(ChatIncomeViewType(chat))
                    }
                }
                adapter.items = viewTypeArray

            } else {
                showToast("Some error occurs.")
            }
        }
    }

    override fun setOnClickListener() {
        binding.imageViewNavBack.setOnClickListener(this)
        binding.imageViewPremium.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.imageViewPremium.id -> {
                showToast("premium")
                findNavController().navigate(ChatDetailFragmentDirections.actionChatDetailFragmentToPremiumFragment())
            }
            binding.imageViewNavBack.id -> {
               // showMessageBox()
                 findNavController().popBackStack()
            }
        }
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


}