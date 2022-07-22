package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.data.repository.AdviceRepository
import br.com.passwordkeeper.domain.result.repository.GetAdviceRepositoryResult
import br.com.passwordkeeper.domain.result.usecase.GetAdviceUseCaseResult

class AdviceUseCaseImpl(
    private val adviceRepository: AdviceRepository
) : AdviceUseCase {
    override suspend fun getAdvice(): GetAdviceUseCaseResult {
        return when(val getAdviceRepositoryResult = adviceRepository.getAdvice()) {
            is GetAdviceRepositoryResult.Success -> {
                if (getAdviceRepositoryResult.adviceDomain != null) {
                    val adviceDomain = getAdviceRepositoryResult.adviceDomain
                    GetAdviceUseCaseResult.Success(adviceDomain.convertToAdviceView())
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