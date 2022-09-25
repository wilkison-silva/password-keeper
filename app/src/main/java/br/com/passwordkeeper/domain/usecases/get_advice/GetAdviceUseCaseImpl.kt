package br.com.passwordkeeper.domain.usecases.get_advice

import br.com.passwordkeeper.domain.repository.AdviceRepository
import br.com.passwordkeeper.domain.mapper.AdviceDomainMapper
import br.com.passwordkeeper.domain.repository.GetAdviceRepositoryResult

class GetAdviceUseCaseImpl(
    private val adviceRepository: AdviceRepository,
    private val adviceDomainMapper: AdviceDomainMapper
) : GetAdviceUseCase {

    override suspend operator fun invoke(): GetAdviceUseCaseResult {
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