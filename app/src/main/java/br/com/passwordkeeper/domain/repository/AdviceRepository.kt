package br.com.passwordkeeper.domain.repository

interface AdviceRepository {

    suspend fun getAdvice(): GetAdviceRepositoryResult
}