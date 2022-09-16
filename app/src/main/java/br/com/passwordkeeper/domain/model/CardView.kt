package br.com.passwordkeeper.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CardView(
    val cardId: String,
    val description: String,
    val login: String,
    val password: String,
    @StringRes val category: Int,
    val favorite: Boolean,
    @DrawableRes val iconHeart: Int,
    val date: String
)