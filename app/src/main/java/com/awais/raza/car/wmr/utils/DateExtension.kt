package com.awais.raza.car.wmr.utils

import android.text.format.DateFormat
import java.math.BigInteger
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


fun getRandomNumber(): String {
    return BigInteger(130, Random()).toString(32).uppercase().substring(0, 10)
}

fun Long.getDateAndTime(): String {
    return DateFormat.format("MM-dd-yyyy  hh:mm aa", Date(this)).toString()
}

fun Long.getTime(): String {

    val sdf = SimpleDateFormat("MM-dd-yyyy-hh:mm aa", Locale.getDefault())
    val dateTime = sdf.format(this)
    val currentDate = sdf.parse(sdf.format(Date()))
    val previousDate = this

    //Check hours between current time and message time
    val diffInMilliSecond: Long = currentDate!!.time - previousDate
    val diffInHours: Long = TimeUnit.MILLISECONDS.toHours(diffInMilliSecond)

    return if (diffInHours < 12) dateTime.subSequence(11, 19).toString()
    else if (diffInHours > 48) dateTime.subSequence(0, 10).toString()
    else "Yesterday"
}