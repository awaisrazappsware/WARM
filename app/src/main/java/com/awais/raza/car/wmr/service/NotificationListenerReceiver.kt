package com.awais.raza.car.wmr.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.awais.raza.car.wmr.repo.ChatRepository
import com.awais.raza.car.wmr.utils.isTitleValid
import com.awais.raza.car.wmr.utils.getRandomNumber
import com.awais.raza.car.wmr.model.ChatData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationListenerReceiver : BroadcastReceiver() {

    // ProcessLifecycleOwner provides lifecycle for the whole application process.
    private val applicationScope = ProcessLifecycleOwner.get().lifecycleScope

    @Inject
    lateinit var repository: ChatRepository

    @Inject
    lateinit var notifications: Notification

    override fun onReceive(context: Context?, intent: Intent) {
        saveAllMessage(intent)
        getNotifyDeletedMessage(intent)

//        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
//
//            val activityIntent = Intent(context, MainActivity::class.java)
//
//            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//
//            context.startActivity(activityIntent)
//        }
    }

    private fun saveAllMessage(intent: Intent) {
        //get data from the intent
        val title = intent.getStringExtra("title") ?: ""
        val text = intent.getStringExtra("text") ?: ""
        val time = intent.getLongExtra("time", 0)
        val app = intent.getStringExtra("app") ?: ""
        val id = getRandomNumber()

        val chat = ChatData(id, title, text, time, app)
        applicationScope.launch {
            //check if the message is from Group chat
            if (title.contains("messages") || title.contains(":")) {
                //Split the title because it contains the group name and user name
                var group = title.substringBefore(":")
                val user = title.substringAfter(": ")
                //Split the group if displays the number of unread messages
                if (group.endsWith("messages)")) group = group.split(" (")[0]

                val groupChat = ChatData(id, group, "$user: $text", time, app, isGroup = true)
                if (text.isTitleValid()) repository.saveMessage(groupChat)
            } else {
                if (title.isTitleValid()) repository.saveMessage(chat)
            }
        }
    }

    private fun getNotifyDeletedMessage(intent: Intent) {
        val isDelete = intent.getBooleanExtra("isDeleted", false)
        val title = intent.getStringExtra("title") ?: ""
        val app = intent.getStringExtra("app") ?: ""

        if (isDelete) {
            applicationScope.launch {
                val lastMessage = repository.getAllLastMessage(title)
                lastMessage?.let {
                    if (!lastMessage.isDeleted) {
                        //if the message is deleted notify the user and change delete state to deleted
                        notifications.notify(
                            title,
                            "$title deleted a message",
                            "Click here to see",
                            app
                        )
                        repository.messageIsDeleted(lastMessage.id)
                    }
                }
            }
        }
    }
}
