package br.com.passwordkeeper.domain.mapper

import br.com.passwordkeeper.domain.model.UserData
import br.com.passwordkeeper.domain.model.UserDomain

class UserDataMapper : BaseMapper<UserData, UserDomain>() {

    override fun transform(model: UserData): UserDomain {
        return UserDomain(
            email = model.email,
            name = model.name
        )
    }

}