package com.avilanii.attend.features.event.presentation.models

import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.features.event.domain.Participant
import com.avilanii.attend.features.event.domain.ParticipantStatus
import kotlinx.serialization.Serializable

@Serializable
data class ParticipantUi(
    val id: Int,
    val name: String,
    val email: String,
    val status: ParticipantStatus,
    val tier: AttendeeTier
)

fun Participant.toParticipantUi(): ParticipantUi{
    return ParticipantUi(
        id = id,
        name = name,
        email = email.value,
        status = status,
        tier = tier ?: AttendeeTier(-1, "Unassigned")
    )
}

fun List<ParticipantUi>.toCSV(): String {
    fun escape(field: String): String =
        if (field.contains(',') || field.contains('"') || field.contains('\n')) {
            "\"${field.replace("\"", "\"\"")}\""
        } else field

    val rows = listOf(listOf("Name", "Email", "Status", "Tier")) +
            this.map { listOf(it.name, it.email, it.status.name, it.tier.title) }

    return rows.joinToString("\n") { row ->
        row.joinToString(",") { escape(it) }
    }
}