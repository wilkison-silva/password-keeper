package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.model.CardData
import br.com.passwordkeeper.domain.model.CardDomain
import br.com.passwordkeeper.domain.model.CardFirestore
import br.com.passwordkeeper.domain.result.repository.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await


class FirebaseCardRepositoryImpl(
    private val fireStore: FirebaseFirestore
) : CardRepository {

    override suspend fun getAllCards(email: String): GetAllCardsRepositoryResult {
        try {
            val userDocumentReference = getUserDocumentReference(email)
            val querySnapshot = fireStore
                .collection(COLLECTION_CARDS)
                .whereEqualTo(FIELD_USER, userDocumentReference).get().await()
            val cardDomainList: List<CardDomain> =
                querySnapshot.documents.mapNotNull { documentSnapshot: DocumentSnapshot? ->
                    documentSnapshot
                        ?.toObject<CardFirestore>()
                        ?.convertToCardData(documentSnapshot.id)
                        ?.convertToCardDomain()
                }
            return GetAllCardsRepositoryResult.Success(cardDomainList)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return GetAllCardsRepositoryResult.ErrorUnknown
    }

    private suspend fun getUserDocumentReference(email: String): DocumentReference {
        val documentSnapshot =
            fireStore.collection(COLLECTION_USERS).document(email).get().await()
        return documentSnapshot.reference
    }

    override suspend fun getCardById(cardId: String): GetCardByIdRepositoryResult {
        try {
            val documentSnapshot =
                fireStore.collection(COLLECTION_CARDS).document(cardId).get().await()
            documentSnapshot.toObject<CardFirestore>()?.convertToCardData(cardId)?.let { cardData ->
                return GetCardByIdRepositoryResult.Success(cardData.convertToCardDomain())
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return GetCardByIdRepositoryResult.ErrorUnknown
    }

    override suspend fun createCard(
        cardData: CardData,
        emailUser: String
    ): CreateCardRepositoryResult {
        try {
            val userDocumentReference = getUserDocumentReference(emailUser)
            val cardDocumentReference = fireStore.collection(COLLECTION_CARDS).document()
            cardDocumentReference
                .set(cardData.convertToCardFireStore(userDocumentReference))
                .await()
            return CreateCardRepositoryResult.Success(cardDocumentReference.id)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return CreateCardRepositoryResult.ErrorUnknown
    }

    override suspend fun updateCard(
        cardData: CardData,
        emailUser: String
    ): UpdateCardRepositoryResult {
        try {
            cardData.cardId?.let { cardId: String ->
                val userDocumentReference = getUserDocumentReference(emailUser)
                val cardDocumentReference = fireStore.collection(COLLECTION_CARDS).document(cardId)
                cardDocumentReference
                    .set(cardData.convertToCardFireStore(userDocumentReference))
                    .await()
                return UpdateCardRepositoryResult.Success(cardDocumentReference.id)
            }
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

}