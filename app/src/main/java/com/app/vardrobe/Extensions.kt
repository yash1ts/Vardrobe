package com.app.vardrobe

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun View.snackBar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration)
}

fun Context.savePreferences(key: String, data: Any) =
    getSharedPreferences("main", Context.MODE_PRIVATE).edit().apply {
        when (data) {
            is Boolean -> putBoolean(key, data)
            is Int -> putInt(key, data)
            is Float -> putFloat(key, data)
            is String -> putString(key, data)
            is Long -> putLong(key, data)
            else -> return
        }
    }.apply()