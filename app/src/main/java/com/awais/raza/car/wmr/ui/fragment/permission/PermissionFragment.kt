package com.awais.raza.car.wmr.ui.fragment.permission

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.awais.raza.car.warm.R
import com.awais.raza.car.wmr.base.fragment.BaseFragment
import com.awais.raza.car.warm.databinding.FragmentPermissionBinding
import com.awais.raza.car.wmr.utils.introductionFinished

class PermissionFragment : BaseFragment(R.layout.fragment_permission), SlidePolicy {

    private var _binding: FragmentPermissionBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPermissionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setObservers()
        setOnClickListener()
    }

    override fun setOnClickListener() {
        binding.notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
            }
        }
    }

    override fun initialize() {
    }

    override fun setObservers() {
    }

    override val isPolicyRespected: Boolean
        get() = isNotificationServiceEnabled()

    override fun onUserIllegallyRequestedNextPage() {
        showToast("Please enable notification permission")
    }

    override fun onResume() {
        super.onResume()
        if (isPolicyRespected) {
            findNavController().navigate(PermissionFragmentDirections.actionPermissionFragmentToChatListingFragment())
            requireContext().introductionFinished()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val pkgName = requireActivity().packageName
        val flat: String? = Settings.Secure.getString(
            requireActivity().contentResolver,
            "enabled_notification_listeners"
        )
        flat?.let {
            val names = flat.split(":").toTypedArray()
            for (i in names.indices) {
                val cn = ComponentName.unflattenFromString(names[i])
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.packageName)) {
                        return true
                    }
                }
            }
        }
        return false
    }
}

interface SlidePolicy {
    /**
     * Whether the user has fulfilled the slides policy and should be allowed to navigate through the intro further.
     * If false is returned, [.onUserIllegallyRequestedNextPage] will be called.
     *
     * @return True if the user should be allowed to leave the slide, else false.
     */
    val isPolicyRespected: Boolean

    /**
     * Called if a user tries to go to the next slide while into navigation has been locked.
     */
    fun onUserIllegallyRequestedNextPage()
}
