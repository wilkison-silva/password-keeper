package br.com.passwordkeeper.extensions

import android.util.Patterns

fun String.countWords(): Int {
    val wordsList = this.trim().split(" ")
    return wordsList.size
}

fun String.getFirstLetter(): String {
    return this.trim()[0].toString()
}

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}