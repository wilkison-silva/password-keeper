package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.mapper.CardDataMapper
import br.com.passwordkeeper.domain.mapper.CardFirestoreMapper
import br.com.passwordkeeper.domain.model.CardData
import br.com.passwordkeeper.domain.model.CardDomain
import br.com.passwordkeeper.domain.model.CardFirestore
import br.com.passwordkeeper.domain.result.repository.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import kotlin.math.log


class FirebaseCardRepositoryImpl(
    private val fireStore: FirebaseFirestore,
    private val cardFirestoreMapper: CardFirestoreMapper,
    private val cardDataMapper: CardDataMapper
) : CardRepository {

    private var userDocumentReference: DocumentReference? = null

    override suspend fun getAllCards(email: String): GetAllCardsRepositoryResult {
        try {
            val querySnapshot = fireStore
                .collection(COLLECTION_CARDS)
                .whereEqualTo(FIELD_USER, getUserDocumentReference(email)).get().await()
            val cardDomainList: List<CardDomain> = getCardDomainListFromQuerySnapshot(querySnapshot)
            return GetAllCardsRepositoryResult.Success(cardDomainList)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return GetAllCardsRepositoryResult.ErrorUnknown
    }

    override suspend fun getCardById(cardId: String): GetCardByIdRepositoryResult {
        try {
            val documentSnapshot =
                fireStore.collection(COLLECTION_CARDS).document(cardId).get().await()
            val cardFirestore = documentSnapshot.toObject<CardFirestore>() as CardFirestore
            val cardData = cardFirestoreMapper.transform(cardFirestore)
            val cardDomain = cardDataMapper.transform(cardData)
            return GetCardByIdRepositoryResult.Success(cardDomain)

        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return GetCardByIdRepositoryResult.ErrorUnknown
    }

    override suspend fun createCard(
        description: String,
        login: String,
        password: String,
        category: String,
        isFavorite: Boolean,
        date: String,
        emailUser: String,
    ): CreateCardRepositoryResult {
        try {
            val cardDocumentReference = fireStore.collection(COLLECTION_CARDS).document()
            val cardFirestore = CardFirestore(
                id = cardDocumentReference.id,
                description = description,
                login = login,
                password = password,
                category = category,
                user = getUserDocumentReference(emailUser),
                favorite = isFavorite,
                date = date
            )
            cardDocumentReference.set(cardFirestore).await()
            return CreateCardRepositoryResult.Success(cardDocumentReference.id)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return CreateCardRepositoryResult.ErrorUnknown
    }

    override suspend fun updateCard(
        cardId: String,
        description: String,
        login: String,
        password: String,
        category: String,
        isFavorite: Boolean,
        date: String,
        emailUser: String
    ): UpdateCardRepositoryResult {
        try {
            val cardDocumentReference = fireStore.collection(COLLECTION_CARDS).document(cardId)
            val cardFirestore = CardFirestore(
                description = description,
                login = login,
                password = password,
                category = category,
                user = getUserDocumentReference(emailUser),
                favorite = isFavorite,
                date = date
            )
            cardDocumentReference.set(cardFirestore).await()
            return UpdateCardRepositoryResult.Success(cardDocumentReference.id)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return UpdateCardRepositoryResult.ErrorUnknown
    }

    override suspend fun deleteCard(cardId: String): DeleteCardRepositoryResult {
        try {
            fireStore.collection(COLLECTION_CARDS).document(cardId).delete()
            return DeleteCardRepositoryResult.Success
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return DeleteCardRepositoryResult.ErrorUnknown
    }

    override suspend fun getFavorites(email: String): GetFavoriteCardsRepositoryResult {
        try {
            val querySnapshot = fireStore
                .collection(COLLECTION_CARDS)
                .whereEqualTo(FIELD_USER, getUserDocumentReference(email))
                .whereEqualTo(FIELD_FAVORITE, true)
                .get().await()

            val cardDomainList: List<CardDomain> = getCardDomainListFromQuerySnapshot(querySnapshot)
            return GetFavoriteCardsRepositoryResult.Success(cardDomainList)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return GetFavoriteCardsRepositoryResult.ErrorUnknown
    }

    override suspend fun getCardsByCategory(
        category: String,
        email: String,
    ): GetCardsByCategoryRepositoryResult {
        try {
            val querySnapshot = fireStore
                .collection(COLLECTION_CARDS)
                .whereEqualTo(FIELD_USER, getUserDocumentReference(email))
                .whereEqualTo(FIELD_CATEGORY, category)
                .get().await()

            val cardDomainList: List<CardDomain> = getCardDomainListFromQuerySnapshot(querySnapshot)

            return GetCardsByCategoryRepositoryResult.Success(cardDomainList)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return GetCardsByCategoryRepositoryResult.ErrorUnknown
    }

    private suspend fun getUserDocumentReference(email: String): DocumentReference {
        userDocumentReference?.let {
            return it
        }

        val documentSnapshot =
            fireStore.collection(COLLECTION_USERS).document(email).get().await()
        userDocumentReference = documentSnapshot.reference
        return documentSnapshot.reference
    }

    private fun getCardDomainListFromQuerySnapshot(
        querySnapshot: QuerySnapshot,
    ): List<CardDomain> {
        return querySnapshot.documents.mapNotNull { documentSnapshot: DocumentSnapshot? ->
            val cardFirestore = documentSnapshot?.toObject<CardFirestore>() as CardFirestore
            val cardData = cardFirestoreMapper.transform(cardFirestore)
            cardDataMapper.transform(cardData)
        }
    }

}