package br.com.passwordkeeper.extensions

import android.util.Patterns
import java.util.regex.Pattern

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

fun String.isValidPassword(): Boolean {
    val passwordREGEX = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=])" +
                "(?=\\S+$)" +
                ".{16,}" +
                "$"
    )
    return passwordREGEX.matcher(this).matches()
}