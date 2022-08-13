package br.com.passwordkeeper.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.result.viewmodelstate.GetAdviceStateResult
import br.com.passwordkeeper.domain.result.usecase.GetAdviceUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.GetCurrentUserUseCaseResult
import br.com.passwordkeeper.domain.result.viewmodelstate.CurrentUserState
import br.com.passwordkeeper.domain.usecase.AdviceUseCase
import br.com.passwordkeeper.domain.usecase.SignInUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val adviceUseCase: AdviceUseCase,
    private val signInUseCase: SignInUseCase,
) : ViewModel() {

    private val _adviceState = MutableLiveData<GetAdviceStateResult>()
    val adviceState: LiveData<GetAdviceStateResult>
        get() = _adviceState

    fun updateAdvice() {
        viewModelScope.launch {
            _adviceState.postValue(GetAdviceStateResult.Loading)
            when (val getAdviceUseCaseResult = adviceUseCase.getAdvice()) {
                is GetAdviceUseCaseResult.Success -> {
                    _adviceState.postValue(GetAdviceStateResult.Success(getAdviceUseCaseResult.adviceView))
                }
                is GetAdviceUseCaseResult.SuccessAdviceWithoutMessage -> {
                    _adviceState.postValue(GetAdviceStateResult.SuccessWithoutMessage)
                }
                is GetAdviceUseCaseResult.ErrorUnknown -> {
                    _adviceState.postValue(GetAdviceStateResult.ErrorUnknown)
                }
            }
        }
    }

    private val _currentUserState = MutableLiveData<CurrentUserState>()
    val currentUserState: LiveData<CurrentUserState>
        get() = _currentUserState

    fun updateCurrentUser() {
        viewModelScope.launch {
            when (val signInUseCaseResult = signInUseCase.getCurrentUser()) {
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