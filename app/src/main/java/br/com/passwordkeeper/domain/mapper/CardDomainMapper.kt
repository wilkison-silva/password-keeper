package br.com.passwordkeeper.domain.mapper

import br.com.passwordkeeper.R
import br.com.passwordkeeper.domain.model.CardDomain
import br.com.passwordkeeper.domain.model.CardView
import br.com.passwordkeeper.domain.model.Categories
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CardDomainMapper : BaseMapper<CardDomain, CardView>() {

    override fun transform(model: CardDomain): CardView {
        return CardView(
            cardId = model.cardId,
            description = model.description,
            login = model.login,
            password = model.password,
            category = getNameForCategory(model.category),
            favorite = model.isFavorite,
            iconHeart = getIconHeart(model.isFavorite),
            dateAsString = model.date,
            date = getDateFromString(model.date)
        )
    }

    private fun getDateFromString(dateAsString: String) : LocalDate {
        return LocalDate.parse(dateAsString, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }

    private fun getNameForCategory(category: String): Int {
        return when (category) {
            Categories.STREAMING.name -> R.string.streaming
            Categories.SOCIAL_MEDIA.name -> R.string.social_media
            Categories.BANKS.name -> R.string.banks
            Categories.EDUCATION.name -> R.string.education
            Categories.WORK.name -> R.string.work
            Categories.CARD.name -> R.string.cards
            else -> R.string.unknown_category
        }
    }

    private fun getIconHeart(isFavorite: Boolean): Int {
        return if (isFavorite) {
            R.drawable.ic_heart_full
        } else {
            R.drawable.ic_heart_empty
        }
    }

}