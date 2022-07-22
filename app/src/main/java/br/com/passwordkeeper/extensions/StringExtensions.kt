package br.com.passwordkeeper.extensions

fun String.countWords(): Int {
    val wordsList = this.trim().split(" ")
    return wordsList.size
}

fun String.getFirstLetter(): String {
    return this.trim()[0].toString()
}