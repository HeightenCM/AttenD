package com.avilanii.attend.core.domain

enum class NetworkError : Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN,
    NOT_FOUND,
    CONFLICT,
    BAD_REQUEST
}