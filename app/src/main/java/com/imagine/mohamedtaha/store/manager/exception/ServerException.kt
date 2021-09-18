package com.imagine.mohamedtaha.store.manager.exception

import java.lang.RuntimeException

class ServerException(message: String, code: Int) : RuntimeException(message) {
    var code = code
    internal set
}