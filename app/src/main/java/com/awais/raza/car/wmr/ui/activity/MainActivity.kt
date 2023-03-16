package com.awais.raza.car.wmr.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.awais.raza.car.warm.R
import com.awais.raza.car.warm.databinding.ActivityMainBinding
import com.awais.raza.car.wmr.base.activity.BaseActivity
import com.awais.raza.car.wmr.service.NotificationListenerService
import com.awais.raza.car.wmr.ui.fragment.chatList.ChatAppTypeEnum


class MainActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHost: NavHostFragment
    private lateinit var navController: NavController

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
    }

    override fun initialize() {
        setUpMain()
        checkAppInstalled()
        navigateToChatDetail()
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onClick(v: View?) {
    }

    private fun setUpMain() {
        //Start the service
        val intent = Intent(applicationContext, NotificationListenerService::class.java)
        startService(intent)

        navHost = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHost.navController
    }

    private fun navigateToChatDetail() {
        val notificationDeleted = intent.getBooleanExtra("notificationDeleted", false)
        val user = intent.getStringExtra("user")
        val app = intent.getStringExtra("app")
        if (notificationDeleted) navHost.findNavController()
            .navigate(R.id.chatDetailFragment, bundleOf("user" to user, "app" to app))
    }

    private fun checkAppInstalled() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val appInstallCheck = prefs.getBoolean("appCheck", false)
        if (!appInstallCheck) {
            if (isAppInstalled(ChatAppTypeEnum.WHATSAPP.getValue())) {
                prefs.edit().putBoolean("appCheck", true).apply()
                prefs.edit().putString("app", ChatAppTypeEnum.WHATSAPP.getValue()).apply()
            } else if (isAppInstalled(ChatAppTypeEnum.WHATSAPP_BUSINESS.getValue())) {
                prefs.edit().putBoolean("appCheck", true).apply()
                prefs.edit().putString("app", ChatAppTypeEnum.WHATSAPP_BUSINESS.getValue()).apply()
            } else {
                prefs.edit().putBoolean("appCheck", true).apply()
                prefs.edit().putString("app", ChatAppTypeEnum.NONE.getValue()).apply()
            }
            prefs.edit().putBoolean("appCheck", true).apply()
        } else {
            Log.d("HS_APP_LOG_TAG", "App Check successfully")
        }
    }
}