package com.awais.raza.car.wmr.base.activity

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun initialize()

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    open fun isAppInstalled(packageName: String): Boolean {
        return try {
            @Suppress("DEPRECATION")
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (ignored: PackageManager.NameNotFoundException) {
            false
        }
    }
//    fun Context.setLocale(language: String): Context {
//        val locale = Locale(language)
//        Locale.setDefault(locale)
//        val config = resources.configuration
//        config.setLocale(locale)
//        config.setLayoutDirection(locale)
//
//        return createConfigurationContext(config)
//    }
}