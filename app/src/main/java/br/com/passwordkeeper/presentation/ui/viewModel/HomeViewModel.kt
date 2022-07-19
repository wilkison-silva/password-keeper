package br.com.passwordkeeper.presentation.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.passwordkeeper.domain.result.GetAdviceState
import br.com.passwordkeeper.domain.result.GetAdviceUseCaseResult
import br.com.passwordkeeper.domain.usecase.AdviceUseCase

class HomeViewModel(
    private val adviceUseCase: AdviceUseCase
): ViewModel() {

    private val _adviceState = MutableLiveData<GetAdviceState>()
    val adviceState: LiveData<GetAdviceState>
        get() = _adviceState

    suspend fun updateAdvice() {
        val getAdviceUseCaseResult = adviceUseCase.getAdvice()
        when(getAdviceUseCaseResult) {
            is GetAdviceUseCaseResult.Success -> {
                _adviceState.postValue(GetAdviceState.Success(getAdviceUseCaseResult.advice))
            }
            is GetAdviceUseCaseResult.SuccessAdviceWithoutMessage -> {
                _adviceState.postValue(GetAdviceState.SuccessWithoutMessage)
            }
            is GetAdviceUseCaseResult.ErrorUnknown -> {
                _adviceState.postValue(GetAdviceState.ErrorUnknown)
            }
        }
    }
}