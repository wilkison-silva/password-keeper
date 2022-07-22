package br.com.passwordkeeper.domain.model

import br.com.passwordkeeper.extensions.countWords
import br.com.passwordkeeper.extensions.getFirstLetter

data class AdviceDomain(
    val message: String
) {
    fun convertToAdviceView(): AdviceView {
        return AdviceView(
            advice = message,
            firstLetter = message.getFirstLetter(),
            quantityWords = message.countWords()
        )
    }

}