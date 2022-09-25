package br.com.passwordkeeper.presentation.features.home.states

import br.com.passwordkeeper.presentation.model.AdviceView

sealed class GetAdviceState {
    data class Success(val adviceView: AdviceView) : GetAdviceState()
    object SuccessWithoutMessage: GetAdviceState()
    object ErrorUnknown: GetAdviceState()
    object Loading: GetAdviceState()
}