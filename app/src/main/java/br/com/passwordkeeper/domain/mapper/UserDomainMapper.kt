package br.com.passwordkeeper.domain.mapper

import br.com.passwordkeeper.domain.model.UserDomain
import br.com.passwordkeeper.presentation.model.UserView

class UserDomainMapper : BaseMapper<UserDomain, UserView>() {

    override fun transform(model: UserDomain): UserView {
        return UserView(
            name = model.name,
            firstCharacterName = model.name.substring(0, 1),
            email = model.email
        )
    }

}