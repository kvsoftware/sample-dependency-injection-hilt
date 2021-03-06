package com.kvsoftware.dependencyinjectionhilt.domain.interactor.base

abstract class BaseInteractor<Type, in Params>() where Type : Any? {

    abstract suspend operator fun invoke(params: Params): Type

    companion object {
        private val TAG = BaseInteractor::class.java.name
    }

}