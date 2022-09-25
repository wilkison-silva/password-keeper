package br.com.passwordkeeper.domain.mapper

import br.com.passwordkeeper.domain.model.AdviceDomain
import br.com.passwordkeeper.presentation.model.AdviceView
import br.com.passwordkeeper.extensions.countWords
import br.com.passwordkeeper.extensions.getFirstLetter

class AdviceDomainMapper : BaseMapper<AdviceDomain, AdviceView>() {

    override fun transform(model: AdviceDomain): AdviceView {
        return AdviceView(
            advice = model.message,
            firstLetter = model.message.getFirstLetter(),
            quantityWords = model.message.countWords()
        )
    }

}