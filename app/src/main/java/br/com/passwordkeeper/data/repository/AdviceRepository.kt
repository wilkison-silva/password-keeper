package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.result.repository.GetAdviceRepositoryResult

interface AdviceRepository {

    suspend fun getAdvice(): GetAdviceRepositoryResult
}