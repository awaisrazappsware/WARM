package com.awais.raza.car.wmr.service

import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.widget.Toast
import com.awais.raza.car.warm.R
import com.awais.raza.car.wmr.utils.isAppValid
import com.awais.raza.car.wmr.utils.isTitleValid

class NotificationListenerService : NotificationListenerService() {

    private val nlServiceReceiver = NotificationListenerReceiver()

    override fun onCreate() {
        super.onCreate()
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.awais.raza.car.warm")
        registerReceiver(nlServiceReceiver, intentFilter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Return STICKY to prevent the automatic service termination
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return super.onBind(intent)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        if (sbn != null && sbn.packageName.isAppValid()) {
            sendMessage(sbn)
        }
    }

    private fun sendMessage(sbn: StatusBarNotification) {
        val intent = Intent("com.awais.raza.car.warm")

        val extras = sbn.notification.extras
        val title = extras.getString("android.title")
        val text = extras.getCharSequence("android.text").toString()
        val app = sbn.packageName

        intent.putExtra("time", sbn.notification.`when`)
        intent.putExtra("title", title)
        intent.putExtra("text", text)
        intent.putExtra("app", app)

        if (title != null && title.isTitleValid()) {
            sendBroadcast(intent)
        }

        //Detect if message gets deleted
        if (text == getString(R.string.deleted_message) || text == getString(R.string.signal_deleted_message)) {
            intent.putExtra("isDeleted", true)
            intent.putExtra("title", title)
            sendBroadcast(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            unregisterReceiver(nlServiceReceiver)
        } catch (e: IllegalArgumentException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
//            if (BuildConfig.DEBUG) {
//                e.printStackTrace()

        }
    }
}