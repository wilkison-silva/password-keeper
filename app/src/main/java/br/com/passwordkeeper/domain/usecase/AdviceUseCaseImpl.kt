package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.repository.AdviceRepository
import br.com.passwordkeeper.domain.mapper.AdviceDomainMapper
import br.com.passwordkeeper.domain.result.repository.GetAdviceRepositoryResult
import br.com.passwordkeeper.domain.result.usecase.GetAdviceUseCaseResult

class AdviceUseCaseImpl(
    private val adviceRepository: AdviceRepository,
    private val adviceDomainMapper: AdviceDomainMapper
) : AdviceUseCase {
    override suspend fun getAdvice(): GetAdviceUseCaseResult {
        return when(val getAdviceRepositoryResult = adviceRepository.getAdvice()) {
            is GetAdviceRepositoryResult.Success -> {
                if (getAdviceRepositoryResult.adviceDomain != null) {
                    val adviceDomain = getAdviceRepositoryResult.adviceDomain
                    GetAdviceUseCaseResult.Success(adviceDomainMapper.transform(adviceDomain))
                } else {
                    GetAdviceUseCaseResult.SuccessAdviceWithoutMessage
                }
            }
            is GetAdviceRepositoryResult.ErrorUnknown -> {
                GetAdviceUseCaseResult.ErrorUnknown
            }
        }
    }
}