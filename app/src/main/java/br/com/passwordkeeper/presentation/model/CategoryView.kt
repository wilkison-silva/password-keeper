package br.com.passwordkeeper.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import br.com.passwordkeeper.commons.Categories

data class CategoryView(
    val category: Categories,
    @StringRes val nameAsStringRes: Int,
    val quantity: Int,
    @DrawableRes val imageAsDrawableRes: Int
)