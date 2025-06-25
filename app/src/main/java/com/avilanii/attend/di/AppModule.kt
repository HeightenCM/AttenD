package com.avilanii.attend.di

import com.avilanii.attend.core.data.HttpClientFactory
import com.avilanii.attend.features.account.data.RemoteAccountMenuDataSource
import com.avilanii.attend.features.account.domain.AccountMenuDataSource
import com.avilanii.attend.features.account.presentation.accountmenu.AccountMenuViewModel
import com.avilanii.attend.features.auth.data.networking.RemoteAuthDataSource
import com.avilanii.attend.features.auth.domain.AuthDataSource
import com.avilanii.attend.features.event.data.networking.RemoteAttendingEventDataSource
import com.avilanii.attend.features.event.data.networking.RemoteEventDataSource
import com.avilanii.attend.features.event.data.networking.RemoteParticipantDataSource
import com.avilanii.attend.features.event.domain.AttendingEventDataSource
import com.avilanii.attend.features.event.domain.EventDataSource
import com.avilanii.attend.features.event.domain.ParticipantDataSource
import com.avilanii.attend.features.event.presentation.attending_events.AttendingEventsListViewModel
import com.avilanii.attend.features.event.presentation.event_list.EventListViewModel
import com.avilanii.attend.features.event.presentation.event_participants.ParticipantListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    single<android.content.ContentResolver> { androidContext().contentResolver }

    singleOf(::RemoteEventDataSource).bind<EventDataSource>()
    singleOf(::RemoteAttendingEventDataSource).bind<AttendingEventDataSource>()
    singleOf(::RemoteParticipantDataSource).bind<ParticipantDataSource>()
    singleOf(::RemoteAuthDataSource).bind<AuthDataSource>()
    singleOf(::RemoteAccountMenuDataSource).bind<AccountMenuDataSource>()


    viewModelOf(::EventListViewModel)
    viewModelOf(::AttendingEventsListViewModel)
    viewModelOf(::AccountMenuViewModel)
    viewModel{ (eventId: Int) -> ParticipantListViewModel(
        get(),
        eventId,
        get()) }
}