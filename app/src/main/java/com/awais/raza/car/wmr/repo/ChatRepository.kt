package com.awais.raza.car.wmr.repo

import androidx.lifecycle.LiveData
import com.awais.raza.car.wmr.database.ChatDatabase
import com.awais.raza.car.wmr.model.ChatData
import com.awais.raza.car.wmr.model.MessageDeleteData
import com.awais.raza.car.wmr.utils.isTitleValid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ChatRepository @Inject constructor(
    private val database: ChatDatabase
) {

    suspend fun saveMessage(chat: ChatData) {
        withContext(Dispatchers.IO) {
            if (chat.message.isTitleValid()) {
                //fetch last message and make comparison to avoid duplicates
                val lastMessage = database.userDao().getAllLastMessage(chat.user)
                if (lastMessage != null) {
                    if (chat.message != lastMessage.message && chat.time != lastMessage.time) database.userDao()
                        .saveChatData(chat)
                } else {
                    database.userDao().saveChatData(chat)
                }
            }
        }
    }
    suspend fun fetchChats(): LiveData<List<ChatData>> {
        return withContext(Dispatchers.IO) {
            database.userDao().getAllChats()
        }
    }

//     fun getAllChatByUser(user: String): Flow<List<ChatData>> {
//            return database.userDao().getAllChatByUser(user)
//    }

    suspend fun fetchMessagesByUser(user: String, app: String): LiveData<List<ChatData>> {
        return withContext(Dispatchers.IO) {
            database.userDao().getMessagesByUser(user, app)
        }
    }

    suspend fun getAllLastMessage(user: String): MessageDeleteData? {
        return withContext(Dispatchers.IO) {
            database.userDao().getAllLastMessage(user)
        }
    }

    suspend fun messageIsDeleted(id: String) {
        withContext(Dispatchers.IO) {
            database.userDao().messageIsDeleted(id)
        }
    }
}