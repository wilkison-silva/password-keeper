package br.com.passwordkeeper.data.repository.source.web.result

import com.google.firebase.auth.FirebaseUser

sealed class FirebaseAuthGetCurrentUserResult {
    data class Success(val firebaseUser: FirebaseUser) : FirebaseAuthGetCurrentUserResult()
    object ErrorNoUserFound: FirebaseAuthGetCurrentUserResult()
}