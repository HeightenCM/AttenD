package com.avilanii.attend.features.event.presentation.models

import com.avilanii.attend.features.event.domain.Participant
import com.avilanii.attend.features.event.domain.ParticipantStatus
import kotlinx.serialization.Serializable

@Serializable
data class ParticipantUi(
    val name: String,
    val email: String,
    val status: ParticipantStatus
)

fun Participant.toParticipantUi(): ParticipantUi{
    return ParticipantUi(
        name = name,
        email = email.value,
        status = status
    )
}

fun List<ParticipantUi>.toCSV(): String {
    fun escape(field: String): String =
        if (field.contains(',') || field.contains('"') || field.contains('\n')) {
            "\"${field.replace("\"", "\"\"")}\""
        } else field

    val rows = listOf(listOf("Name", "Email", "Status")) +
            this.map { listOf(it.name, it.email, it.status.name) }

    return rows.joinToString("\n") { row ->
        row.joinToString(",") { escape(it) }
    }
}