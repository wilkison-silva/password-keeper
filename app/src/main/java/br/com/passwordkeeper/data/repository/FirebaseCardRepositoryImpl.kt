package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.model.CardData
import br.com.passwordkeeper.domain.model.CardFirestore
import br.com.passwordkeeper.domain.result.repository.GetAllCardsRepositoryResult
import br.com.passwordkeeper.domain.result.repository.GetCardByIdRepositoryResult
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
            val cardDataList: List<CardData> =
                querySnapshot.documents.mapNotNull { documentSnapshot: DocumentSnapshot? ->
                    documentSnapshot?.toObject<CardFirestore>()?.convertToCardData()
                }
            return GetAllCardsRepositoryResult.Success(cardDataList)
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
            documentSnapshot.toObject<CardFirestore>()?.convertToCardData()?.let { cardData ->
                return GetCardByIdRepositoryResult.Success(cardData)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return GetCardByIdRepositoryResult.ErrorUnknown
    }

}