package com.avilanii.attend.features.event.presentation.event_list

import com.avilanii.attend.features.event.presentation.models.EventUi

sealed interface EventListAction {
    data class OnEventClick(val eventUi: EventUi): EventListAction
}