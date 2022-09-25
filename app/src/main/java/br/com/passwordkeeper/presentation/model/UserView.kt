package br.com.passwordkeeper.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserView(
    val name: String,
    val firstCharacterName: String,
    val email: String
): Parcelable