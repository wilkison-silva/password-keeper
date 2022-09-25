package br.com.passwordkeeper.domain.repository

import br.com.passwordkeeper.domain.model.AdviceDomain

sealed class GetAdviceRepositoryResult {
    data class Success(val adviceDomain: AdviceDomain?) : GetAdviceRepositoryResult()
    object ErrorUnknown: GetAdviceRepositoryResult()
}