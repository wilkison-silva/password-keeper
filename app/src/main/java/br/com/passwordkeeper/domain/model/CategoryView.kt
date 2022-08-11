package br.com.passwordkeeper.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CategoryView(
    @StringRes val StringResourceId: Int,
    val size: Int,
    @DrawableRes val DrawableResourceId: Int
)