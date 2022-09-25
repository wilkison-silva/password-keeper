package br.com.passwordkeeper.presentation.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.usecases.get_current_user.GetCurrentUserUseCaseResult
import br.com.passwordkeeper.domain.usecases.get_current_user.GetCurrentUserUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _bottomNavigationState = MutableLiveData<BottomNavigationState>()
    val bottomNavigationState: LiveData<BottomNavigationState>
        get() = _bottomNavigationState

    fun updateBottomNavigationVisibility(visibility: Boolean) {
        if (visibility) {
            _bottomNavigationState.postValue(BottomNavigationState.Show)
        }
        else {
            _bottomNavigationState.postValue(BottomNavigationState.Hide)
        }
    }

    private val _currentUserState = MutableLiveData<CurrentUserState>()
    val currentUserState: LiveData<CurrentUserState>
        get() = _currentUserState

    fun updateCurrentUser() {
        viewModelScope.launch {
            when (val signInUseCaseResult = getCurrentUserUseCase()) {
                is GetCurrentUserUseCaseResult.ErrorUnknown -> {
                    _currentUserState
                        .postValue(CurrentUserState.ErrorUnknown)
                }
                is GetCurrentUserUseCaseResult.Success -> {
                    _currentUserState
                        .postValue(CurrentUserState.Success(signInUseCaseResult.userView))
                }
            }
        }
    }

}