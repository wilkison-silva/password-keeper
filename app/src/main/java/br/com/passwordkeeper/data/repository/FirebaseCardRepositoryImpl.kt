package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.model.CardFirestore
import br.com.passwordkeeper.domain.result.repository.GetAllCardsRepositoryResult
import br.com.passwordkeeper.domain.result.repository.GetCardByIdRepositoryResult
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class FirebaseCardRepositoryImpl : CardRepository {

    override suspend fun getAllCards(email: String): GetAllCardsRepositoryResult {
        TODO("Not yet implemented")
    }

    override suspend fun getCardById(cardId: String): GetCardByIdRepositoryResult {
        TODO("Not yet implemented")
    }

    private suspend fun getCardsList(documentSnapshot: DocumentSnapshot): List<CardFirestore> {
        return (documentSnapshot.get("cards") as List<DocumentReference>)
            .mapNotNull { documentReference ->
                documentReference.get().await().toObject<CardFirestore>()
            }
    }
}