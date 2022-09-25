package br.com.passwordkeeper.domain.mapper

import br.com.passwordkeeper.data.model.AdviceData
import br.com.passwordkeeper.domain.model.AdviceDomain

class AdviceDataMapper : BaseMapper<AdviceData, AdviceDomain>() {

    override fun transform(model: AdviceData): AdviceDomain {
        return AdviceDomain(
            message = model.advice
        )
    }

}