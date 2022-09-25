package br.com.passwordkeeper.domain.mapper

import br.com.passwordkeeper.data.model.UserData
import br.com.passwordkeeper.data.model.UserFirestore

class UserFirestoreMapper : BaseMapper<UserFirestore, UserData>() {

    override fun transform(model: UserFirestore): UserData {
        return UserData(
            email = model.email,
            name = model.name
        )
    }

}