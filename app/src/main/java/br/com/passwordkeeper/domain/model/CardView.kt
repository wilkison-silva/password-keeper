package br.com.passwordkeeper.domain.model

import androidx.annotation.DrawableRes

data class CardView(
    val cardId: String,
    val description: String,
    val login: String,
    val password: String,
    val category: String,
    val favorite: Boolean,
    @DrawableRes val iconHeart: Int,
    val date: String
)