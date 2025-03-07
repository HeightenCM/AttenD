package com.avilanii.attend.features.event.presentation.event_create

import com.avilanii.attend.features.event.presentation.models.EventUi

sealed interface EventCreateAction {
    data class OnCreateEventOk(val eventUi: EventUi): EventCreateAction
    data object OnCreateEventCancel: EventCreateAction
}