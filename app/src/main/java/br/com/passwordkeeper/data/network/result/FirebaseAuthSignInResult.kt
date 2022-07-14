package br.com.passwordkeeper.data.network.result

sealed class FirebaseAuthSignInResult {
    object Success : FirebaseAuthSignInResult()
    object ErrorEmailNotFound: FirebaseAuthSignInResult()
    object ErrorPasswordIncorrect: FirebaseAuthSignInResult()
}