package br.com.passwordkeeper.data.datasource.web.result

import com.google.firebase.auth.FirebaseUser

sealed class FirebaseAuthSignInResult {
    data class Success(val firebaseUser: FirebaseUser) : FirebaseAuthSignInResult()
    object ErrorUserNotFound : FirebaseAuthSignInResult()
    object ErrorEmailOrPasswordIncorrect: FirebaseAuthSignInResult()
    object ErrorUnknown: FirebaseAuthSignInResult()
}