package br.com.passwordkeeper.domain.result

import com.google.firebase.auth.FirebaseUser

sealed class GetCurrentUserResult {
    data class Success(val firebaseUser: FirebaseUser) : GetCurrentUserResult()
    object ErrorNoUserFound: GetCurrentUserResult()
}