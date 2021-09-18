package com.imagine.mohamedtaha.store.manager.exception

import java.lang.reflect.Type

class ApplicationException(message: String?) : Throwable(message) {
    private lateinit var type:Type

    override val message: String?
        get() = super.message

    fun getType():Type{
        return type
    }
    fun setType(type:Type){
       this.type = type
    }
    enum class Type{
        NO_INTERNET,NO_DATA , VALIDATION

    }

}