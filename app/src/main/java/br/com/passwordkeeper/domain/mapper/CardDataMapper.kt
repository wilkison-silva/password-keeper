package br.com.passwordkeeper.domain.mapper

import br.com.passwordkeeper.domain.model.CardData
import br.com.passwordkeeper.domain.model.CardDomain
import br.com.passwordkeeper.domain.model.CardFirestore

class CardDataMapper : BaseMapper<CardData, CardDomain>() {

    override fun transform(model: CardData): CardDomain {
        return CardDomain(
            cardId = model.cardId ?: "",
            description = model.description,
            login = model.login,
            password = model.password,
            category = model.category,
            isFavorite = model.isFavorite,
            date = model.date
        )
    }

}