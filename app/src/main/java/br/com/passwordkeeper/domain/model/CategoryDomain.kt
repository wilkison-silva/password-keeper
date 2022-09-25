package br.com.passwordkeeper.domain.model

import br.com.passwordkeeper.commons.Categories

data class CategoryDomain(
    val category: Categories,
    val quantity: Int,
)