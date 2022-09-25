package br.com.passwordkeeper.presentation.features.sign_up.states

sealed class CreateUserState {
    object Success : CreateUserState()
    object ErrorWeakPassword: CreateUserState()
    object ErrorEmailMalformed: CreateUserState()
    object ErrorEmailAlreadyExists: CreateUserState()
    object ErrorUnknown: CreateUserState()
    object EmptyState: CreateUserState()
}