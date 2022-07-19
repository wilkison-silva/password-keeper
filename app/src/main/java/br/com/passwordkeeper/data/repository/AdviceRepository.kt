package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.result.GetAdviceRepositoryResult

interface AdviceRepository {

    suspend fun getAdvice(): GetAdviceRepositoryResult
}