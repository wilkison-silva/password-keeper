package br.com.passwordkeeper.domain.mapper

import br.com.passwordkeeper.domain.model.CardData
import br.com.passwordkeeper.domain.model.CardFirestore

class CardFirestoreMapper : BaseMapper<CardFirestore, CardData>() {

    override fun transform(model: CardFirestore): CardData {
        return CardData(
            description = model.description,
            login = model.login,
            password = model.password,
            category = model.category,
            isFavorite = model.favorite,
            date = model.date
        )
    }

}