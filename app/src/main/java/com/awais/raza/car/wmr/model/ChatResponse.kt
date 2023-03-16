package com.awais.raza.car.wmr.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Keep
@Entity
@Parcelize
data class ChatData(
    @PrimaryKey
    val id: String,
    val user: String,
    val message: String,
    val time: Long,
    @ColumnInfo(defaultValue = "")
    val app: String,
    val isDeleted: Boolean = false,
    val isGroup: Boolean = false,
    var lastMessage:String = "",

) : Parcelable

@Keep
data class MessageDeleteData(
    val id: String = "0",
    val message: String = "",
    val isDeleted: Boolean = false,
    val time: Long,
)

data class PremiumPackageData(
    var title:String,
    var price:String,
    var isChecked:Boolean,
    var discount:String,
)