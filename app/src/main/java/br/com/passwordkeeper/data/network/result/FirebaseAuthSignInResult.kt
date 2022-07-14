package br.com.passwordkeeper.data.network.result

import com.google.firebase.auth.FirebaseUser

sealed class FirebaseAuthSignInResult {
    data class Success(val firebaseUser: FirebaseUser?) : FirebaseAuthSignInResult()
    object ErrorEmailNotFound: FirebaseAuthSignInResult()
    object ErrorPasswordIncorrect: FirebaseAuthSignInResult()
}