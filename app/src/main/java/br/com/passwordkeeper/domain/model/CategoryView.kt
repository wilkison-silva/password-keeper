package br.com.passwordkeeper.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CategoryView(
    val category: Categories,
    @StringRes val nameAsStringRes: Int,
    val quantity: Int,
    @DrawableRes val imageAsDrawableRes: Int
)