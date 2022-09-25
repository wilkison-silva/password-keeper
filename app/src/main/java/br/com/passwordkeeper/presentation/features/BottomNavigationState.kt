package br.com.passwordkeeper.presentation.features

sealed class BottomNavigationState {
    object Show : BottomNavigationState()
    object Hide : BottomNavigationState()
    object EmptyState : BottomNavigationState()
}