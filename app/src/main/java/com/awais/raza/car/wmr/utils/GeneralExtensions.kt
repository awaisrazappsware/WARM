package com.awais.raza.car.wmr.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import com.awais.raza.car.warm.R
import com.awais.raza.car.warm.databinding.CustomDialogBoxBinding
import com.awais.raza.car.warm.databinding.CustomExitDialogBoxBinding
import com.awais.raza.car.wmr.ui.activity.MainActivity
import com.awais.raza.car.wmr.ui.fragment.chatList.ChatAppTypeEnum
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop


fun String.isTitleValid(): Boolean {
    return when (this) {
        "" -> false
        "WhatsApp" -> false
        "Checking for messages…" -> false
        "Deleting messages…" -> false
        "This message was deleted" -> false
        "This message was deleted." -> false
        else -> true
    }
}

fun String.isAppValid(): Boolean {
    return when (this) {
        ChatAppTypeEnum.WHATSAPP.getValue() -> true
        ChatAppTypeEnum.WHATSAPP_BUSINESS.getValue() -> true
        else -> false
    }
}

fun String.name(): String {
    return when (this) {
        ChatAppTypeEnum.WHATSAPP.getValue() -> "WhatsApp"
        ChatAppTypeEnum.WHATSAPP_BUSINESS.getValue() -> "WhatsApp business"
        else -> "Undefined"
    }
}


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.INVISIBLE
}

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun ImageView.loadImage(url: Int) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.ic_chat_user)
        .transform(CircleCrop())
        .into(this)
}


fun <T> Activity.startActivity(activity: Class<T>) {
    startActivity(Intent(this, activity))
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    finish()
}

fun Context.introductionFinished() {
    val editor = this.getSharedPreferences("DATA_STORE", Context.MODE_PRIVATE).edit()
    editor.putBoolean("finishedIntro", true)
    editor.apply()
}

fun Context.isIntroductionFinish(): Boolean {
    val sharedPref = this.getSharedPreferences("DATA_STORE", Context.MODE_PRIVATE)
    return sharedPref.getBoolean("finishedIntro", false)
}

@Suppress("DEPRECATION")
fun showRatingMessageBox(context:Context) {
    // build alert dialog
    val dialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT).create()
    val dialogBinding: CustomExitDialogBoxBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.custom_exit_dialog_box,
        null,
        false
    )
    dialog.setView(dialogBinding.root)
    dialog.setCancelable(true)
    dialog.show()

    dialogBinding.ratingBar.isEnabled = true
    dialogBinding.ratingBar.isClickable = true
    dialogBinding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
        Toast.makeText(context, "Rating : $rating", Toast.LENGTH_SHORT).show()
    }
    dialogBinding.ratingBar.refreshDrawableState()

    dialogBinding.btnExit.setOnClickListener {
        dialog.dismiss()
    }
}

@Suppress("DEPRECATION")
fun showMessageBox(context:Context,activity: Activity) {
    // build alert dialog
    val dialog = AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT).create()
    val dialogBinding: CustomDialogBoxBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.custom_dialog_box,
        null,
        false
    )
    dialog.setView(dialogBinding.root)
    dialog.setCancelable(true)
    dialog.show()
    dialogBinding.btnYes.setOnClickListener {
        (activity as MainActivity).finish()
    }
    dialogBinding.textViewCancel.setOnClickListener {
        dialog.dismiss()
    }
}

// document opener by custom tab
fun openCustomTab(context: Context,url: String?) {
    val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
    val customTabsIntent: CustomTabsIntent = builder.build()
    customTabsIntent.launchUrl(context, Uri.parse(url))
}

//
//fun Activity.isIntroductionFinish(): Boolean {
//    val sharedPref = this.getSharedPreferences("DATA_STORE", Context.MODE_PRIVATE)
//    return sharedPref.getBoolean("finishedIntro", false)
//}
//
//fun Chip.changeBackgroundColor(app: String?) {
//    when (app) {
//        ChatAppTypeEnum.WHATSAPP.getValue() -> setChipBackgroundColorResource(R.color.whatsapp)
//        ChatAppTypeEnum.WHATSAPP_BUSINESS.getValue() -> setChipBackgroundColorResource(R.color.whatsapp_business)
//        else -> this.gone()
//    }
//}
//
//
//
//val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL(
//            "ALTER TABLE ChatData "
//                    + "ADD COLUMN address TEXT"
//        )
//    }
//}