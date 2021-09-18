package com.imagine.mohamedtaha.store.manager.exception

import java.lang.RuntimeException

class ServerError:RuntimeException {
    val errorBody:String
    constructor(message: String):super(message){
        errorBody = ""
    }
    constructor(message:String,errorBody:String):super(message){
        this.errorBody = errorBody
    }
}