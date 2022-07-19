package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.data.repository.AdviceRepository
import br.com.passwordkeeper.domain.result.GetAdviceRepositoryResult
import br.com.passwordkeeper.domain.result.GetAdviceUseCaseResult

class AdviceUseCaseImpl(
    private val adviceRepository: AdviceRepository
) : AdviceUseCase {
    override suspend fun getAdvice(): GetAdviceUseCaseResult {
        return when(val getAdviceRepositoryResult = adviceRepository.getAdvice()) {
            is GetAdviceRepositoryResult.Success -> {
                if (getAdviceRepositoryResult.adviceResponse?.advice != null) {
                    GetAdviceUseCaseResult.Success(getAdviceRepositoryResult.adviceResponse.convertToAdvice())
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