package br.com.passwordkeeper.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.result.viewmodelstate.GetAdviceStateResult
import br.com.passwordkeeper.domain.result.usecase.GetAdviceUseCaseResult
import br.com.passwordkeeper.domain.usecase.AdviceUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val adviceUseCase: AdviceUseCase
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
}