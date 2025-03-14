package com.avilanii.attend.features.event.domain

import android.util.Patterns

data class Participant(
    val name: String,
    val email: Email,
    val status: ParticipantStatus
)

data class Email(val value: String) {
    init {
        require(Patterns.EMAIL_ADDRESS.matcher(value).matches()) { "Invalid email format!" }
    }
}

enum class ParticipantStatus{
    PENDING, ACCEPTED, REJECTED
}

fun String.toParticipantEmail(): Email{
    return Email(this)
}

fun Int.toParticipantStatus(): ParticipantStatus{
    return when(this){
        0 -> ParticipantStatus.ACCEPTED
        1 -> ParticipantStatus.PENDING
        else -> ParticipantStatus.REJECTED
    }
}