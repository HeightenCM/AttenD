package com.avilanii.attend.core.domain.models

import android.util.Patterns

data class Password(val value: String) {
    init {
        require(value.isNotBlank()) { "Invalid password!" }
    }
}

fun String.toPassword(): Email{
    return Email(this)
}
