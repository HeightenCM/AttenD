package com.avilanii.attend.di

import com.avilanii.attend.core.data.HttpClientFactory
import com.avilanii.attend.features.auth.data.networking.RemoteAuthDataSource
import com.avilanii.attend.features.auth.domain.AuthDataSource
import com.avilanii.attend.features.event.data.networking.RemoteEventDataSource
import com.avilanii.attend.features.event.data.networking.RemoteParticipantDataSource
import com.avilanii.attend.features.event.domain.EventDataSource
import com.avilanii.attend.features.event.domain.ParticipantDataSource
import com.avilanii.attend.features.event.presentation.attending_events.AttendingEventsListViewModel
import com.avilanii.attend.features.event.presentation.event_list.EventListViewModel
import com.avilanii.attend.features.event.presentation.event_participants.ParticipantListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteEventDataSource).bind<EventDataSource>()
    singleOf(::RemoteParticipantDataSource).bind<ParticipantDataSource>()
    singleOf(::RemoteAuthDataSource).bind<AuthDataSource>()

    viewModelOf(::EventListViewModel)
    viewModelOf(::AttendingEventsListViewModel)
    viewModel{ (eventId: Int) -> ParticipantListViewModel(get(), eventId) }
}