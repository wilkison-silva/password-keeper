package br.com.passwordkeeper.domain.mapper

import br.com.passwordkeeper.R
import br.com.passwordkeeper.domain.model.CardDomain
import br.com.passwordkeeper.domain.model.CardView

class CardDomainMapper : BaseMapper<CardDomain, CardView>() {

    override fun transform(model: CardDomain): CardView {
        return CardView(
            cardId = model.cardId,
            description = model.description,
            login = model.login,
            password = model.password,
            category = model.category,
            favorite = model.isFavorite,
            iconHeart = getIconHeart(model.isFavorite),
            date = model.date
        )
    }

    private fun getIconHeart(isFavorite: Boolean): Int {
        return if (isFavorite) {
            R.drawable.ic_heart_full
        } else {
            R.drawable.ic_heart_empty
        }
    }

}