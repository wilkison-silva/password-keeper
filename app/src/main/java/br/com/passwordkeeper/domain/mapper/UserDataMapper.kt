package br.com.passwordkeeper.domain.mapper

import br.com.passwordkeeper.domain.model.UserData
import br.com.passwordkeeper.domain.model.UserDomain
import br.com.passwordkeeper.domain.model.UserFirestore
import br.com.passwordkeeper.domain.model.UserView

class UserDataMapper : BaseMapper<UserData, UserDomain>() {

    override fun transform(model: UserData): UserDomain {
        return UserDomain(
            email = model.email,
            name = model.name
        )
    }

}