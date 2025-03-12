package com.avilanii.attend.di

import com.avilanii.attend.core.data.HttpClientFactory
import com.avilanii.attend.features.event.data.networking.RemoteEventDataSource
import com.avilanii.attend.features.event.domain.EventDataSource
import com.avilanii.attend.features.event.presentation.event_list.EventListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteEventDataSource).bind<EventDataSource>()

    viewModelOf(::EventListViewModel)
}