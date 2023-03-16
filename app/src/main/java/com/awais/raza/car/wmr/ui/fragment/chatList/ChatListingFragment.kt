package com.awais.raza.car.wmr.ui.fragment.chatList

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ShareCompat
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import androidx.recyclerview.widget.LinearLayoutManager
import com.awais.raza.car.warm.R
import com.awais.raza.car.wmr.base.adapter.GenericListAdapter
import com.awais.raza.car.wmr.base.adapter.OnItemClickListener
import com.awais.raza.car.wmr.base.adapter.ViewType
import com.awais.raza.car.wmr.base.fragment.BaseFragment
import com.awais.raza.car.warm.databinding.FragmentChatListingBinding
import com.awais.raza.car.wmr.model.ChatData
import com.awais.raza.car.wmr.service.NotificationListenerService
import com.awais.raza.car.wmr.ui.fragment.chatList.viewType.ChatListViewType
import com.awais.raza.car.wmr.viewModel.ChatViewModel
import com.awais.raza.car.wmr.ui.activity.MainActivity
import com.awais.raza.car.wmr.utils.*
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ChatListingFragment : BaseFragment(R.layout.fragment_chat_listing), View.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    private var _binding: FragmentChatListingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatViewModel by viewModels()
    private var viewTypeArray = ArrayList<ViewType<*>>()
    private lateinit var toggle: ActionBarDrawerToggle

    private val adapter by lazy {
        GenericListAdapter(object : OnItemClickListener<ViewType<*>> {
            override fun onItemClicked(view: View, item: ViewType<*>, position: Int) {
                item.data()?.let { data ->
                    (data).also { chatData ->
                        (chatData as ChatData).also {
                            if (position > -1) {
                                val prefs = getDefaultSharedPreferences(requireContext())
                                chatData.lastMessage = chatData.message
                                prefs.edit().putString(chatData.user, chatData.lastMessage).apply()
                                findNavController().navigate(
                                    R.id.action_chatListingFragment_to_chatDetailFragment,
                                    bundleOf("user" to chatData.user, "app" to chatData.app)
                                )
                            }
                        }
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
        _binding = FragmentChatListingBinding.inflate(inflater)
        binding.viewModel = viewModel
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun initialize() {
        setRecyclerView()
        toggle = ActionBarDrawerToggle(
            activity as MainActivity,
            binding.drawerLayout,
            R.string.open,
            R.string.close
        )
    }

    override fun setObservers() {
        val prefs = getDefaultSharedPreferences(requireActivity())
        val app = prefs.getString("app", "defValue")
        viewModel.chatListResponse.observe(viewLifecycleOwner) { data ->
            if (data.isNotEmpty()) {
                binding.noChatGroupLayout.gone()
                when ((app)) {
                    ChatAppTypeEnum.NONE.getValue() -> {
                        showToast("No such app installed")
                        binding.noChatGroupLayout.show()
                    }
                    ChatAppTypeEnum.WHATSAPP.getValue() -> {
                        viewTypeArray.clear()
                        for (chat in data) {
                            if (chat.app != ChatAppTypeEnum.WHATSAPP.getValue()) {
                                binding.noChatGroupLayout.show()
                                break
                            } else {
                                viewTypeArray.add(ChatListViewType(chat))
                            }
                        }
                    }
                    ChatAppTypeEnum.WHATSAPP_BUSINESS.getValue() -> {
                        viewTypeArray.clear()
                        for (chat in data) {
                            if (chat.app != ChatAppTypeEnum.WHATSAPP_BUSINESS.getValue()) {
                                binding.noChatGroupLayout.show()
                                break
                            } else {
                                viewTypeArray.add(ChatListViewType(chat))
                            }
                        }
                    }
                }
                adapter.items = viewTypeArray
            } else {
                binding.noChatGroupLayout.show()
            }
        }
    }

    override fun setOnClickListener() {
        binding.imageViewMoreOption.setOnClickListener(this)
        binding.imageViewPremium.setOnClickListener(this)
        binding.navView.setNavigationItemSelectedListener(this)
        binding.navView.setCheckedItem(R.id.navWhatsApp)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.imageViewMoreOption.id -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                toggle.syncState()
            }
            binding.imageViewPremium.id -> {
                findNavController().navigate(ChatListingFragmentDirections.
                actionChatListingFragmentToPremiumFragment())
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val prefs = getDefaultSharedPreferences(requireActivity())
        val app = prefs.edit().putString("appSelection","def value").apply()
        if(  app.equals(ChatAppTypeEnum.WHATSAPP.getValue())){
            item.isCheckable = true
        }
        when (item.itemId) {
            R.id.navWhatsApp -> {
                prefs.edit().putString("app", ChatAppTypeEnum.WHATSAPP.getValue()).apply()
                prefs.edit().putString("appSelection", ChatAppTypeEnum.WHATSAPP.getValue()).apply()
                lifecycleScope.launch {
                    delay(500)
                    setObservers()
                }

            }
            R.id.navWhatsAppBusiness -> {
                prefs.edit().putString("app", ChatAppTypeEnum.WHATSAPP_BUSINESS.getValue()).apply()
                prefs.edit().putString("appSelection", ChatAppTypeEnum.WHATSAPP_BUSINESS.getValue()).apply()
                lifecycleScope.launch {
                    delay(500)
                    setObservers()
                }
            }
            R.id.navRestart -> {
                //Start the service
                val intent = Intent(
                    requireContext().applicationContext,
                    NotificationListenerService::class.java
                )
                requireActivity().startService(intent)
            }

            R.id.navHowToUse -> {
                findNavController().navigate(ChatListingFragmentDirections.actionChatListingFragmentToSliderFragment("chat"))
            }
            R.id.navPremium -> {
                findNavController().navigate(ChatListingFragmentDirections.
                actionChatListingFragmentToPremiumFragment())
            }
            R.id.navShare -> {
                @Suppress("DEPRECATION")
                ShareCompat.IntentBuilder.from(requireActivity())
                    .setType("text/plain")
                    .setChooserTitle("Share App")
                    .setText("http://play.google.com/store/apps/details?id=" +
                            requireActivity().packageName)
                    .startChooser()
            }

            R.id.navPolicies -> {
                openCustomTab(requireContext(),"https://sites.google.com/view/wamrd")
            }
            R.id.navFeedBack -> {
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.type = "text/plain"
                startActivity(emailIntent)
            }
            R.id.navAppUpdate -> {
            }

            R.id.navRateUs -> {
                showRatingMessageBox(requireActivity())
            }
            R.id.navMoreApp -> {
                openCustomTab(requireContext(),"https://play.google.com/store/apps/dev?id=7881149356682535296")
            }
        }
        binding.drawerLayout.closeDrawers()
        return true

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
                    if (binding.drawerLayout.isOpen) {
                        binding.drawerLayout.closeDrawers()
                    } else {
                        showMessageBox(requireActivity(),activity as MainActivity)
                    }
                }
            })
    }
}