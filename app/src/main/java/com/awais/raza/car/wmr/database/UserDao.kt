package com.awais.raza.car.wmr.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.awais.raza.car.wmr.model.ChatData
import com.awais.raza.car.wmr.model.MessageDeleteData


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveChatData(data: ChatData)

    @Query("SELECT * from chatData group by user,app order by MAX(time) DESC")
    fun getAllChats(): LiveData<List<ChatData>>

//    @Query("SELECT * FROM chatData WHERE user LIKE :query")
//    fun getAllChatByUser(query: String): Flow<List<ChatData>>

    @Query("SELECT * from chatData where user =:user and app = :app order by time DESC")
    fun getMessagesByUser(user: String, app: String): LiveData<List<ChatData>>

    @Query("SELECT id,message,isDeleted,time from chatData where user =:user order by time DESC LIMIT 1")
    fun getAllLastMessage(user: String): MessageDeleteData?

    @Query("UPDATE chatData set isDeleted =1 where id =:id")
    fun messageIsDeleted(id: String)

    @Query("DELETE FROM chatData")
    fun clearChatData()
}
