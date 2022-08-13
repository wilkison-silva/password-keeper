package br.com.passwordkeeper.domain.result.viewmodelstate

sealed class BottomNavigationState {
    object Show : BottomNavigationState()
    object Hide : BottomNavigationState()
}