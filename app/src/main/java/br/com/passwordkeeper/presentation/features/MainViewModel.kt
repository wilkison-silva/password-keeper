package br.com.passwordkeeper.presentation.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.usecases.get_current_user.GetCurrentUserUseCase
import br.com.passwordkeeper.domain.usecases.get_current_user.GetCurrentUserUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _bottomNavigationState =
        MutableStateFlow<BottomNavigationState>(BottomNavigationState.EmptyState)
    val bottomNavigationState = _bottomNavigationState.asStateFlow()

    fun updateBottomNavigationVisibility(visibility: Boolean) {
        if (visibility) {
            _bottomNavigationState.value = BottomNavigationState.Show
        } else {
            _bottomNavigationState.value = BottomNavigationState.Hide
        }
    }

    private val _currentUserState = MutableStateFlow<CurrentUserState>(CurrentUserState.EmptyState)
    val currentUserState = _currentUserState.asStateFlow()

    fun updateCurrentUser() {
        viewModelScope.launch {
            when (val signInUseCaseResult = getCurrentUserUseCase()) {
                is GetCurrentUserUseCaseResult.ErrorUnknown -> {
                    _currentUserState.value = CurrentUserState.ErrorUnknown
                }
                is GetCurrentUserUseCaseResult.Success -> {
                    _currentUserState.value = CurrentUserState.Success(signInUseCaseResult.userView)
                }
            }
        }
    }

}