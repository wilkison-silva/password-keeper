package br.com.passwordkeeper.domain.mapper

import br.com.passwordkeeper.domain.model.UserData
import br.com.passwordkeeper.domain.model.UserFirestore

class UserFirestoreMapper : BaseMapper<UserFirestore, UserData>() {

    override fun transform(model: UserFirestore): UserData {
        return UserData(
            email = model.email,
            name = model.name
        )
    }

}