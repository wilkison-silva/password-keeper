package br.com.passwordkeeper.domain.mapper

import br.com.passwordkeeper.data.model.CardData
import br.com.passwordkeeper.data.model.CardFirestore

class CardFirestoreMapper : BaseMapper<CardFirestore, CardData>() {

    override fun transform(model: CardFirestore): CardData {
        return CardData(
            cardId = model.id,
            description = model.description,
            login = model.login,
            password = model.password,
            category = model.category,
            isFavorite = model.favorite,
            date = model.date
        )
    }

}