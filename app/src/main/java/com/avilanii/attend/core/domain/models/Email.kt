package com.avilanii.attend.core.domain.models

import android.util.Patterns

data class Email(val value: String) {
    init {
        require(Patterns.EMAIL_ADDRESS.matcher(value).matches()) { "Invalid email format!" }
    }
}

fun String.toEmail(): Email{
    return Email(this)
}