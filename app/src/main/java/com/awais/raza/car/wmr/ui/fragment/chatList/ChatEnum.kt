package com.awais.raza.car.wmr.ui.fragment.chatList

enum class ChatAppTypeEnum(private val type: String) {
    NONE(""),
    WHATSAPP("com.whatsapp"),
    WHATSAPP_BUSINESS("com.whatsapp.w4b");

    fun getValue(): String{
        return type
    }
}
