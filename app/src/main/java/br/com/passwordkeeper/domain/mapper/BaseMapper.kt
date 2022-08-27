package br.com.passwordkeeper.domain.mapper

abstract class BaseMapper<T, R> {

    abstract fun transform(model: T): R

    fun transform(modelList: List<T>): List<R> {
        return modelList.map{ transform(it) }
    }

}