package com.avilanii.attend

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.core.MultiProcessDataStoreFactory
import com.avilanii.attend.core.data.UserPreferences
import com.avilanii.attend.core.data.UserPreferencesSerializer
import com.avilanii.attend.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.io.File

class AttenDApp: Application() {
    val userPrefsStore: DataStore<UserPreferences> by lazy {
        MultiProcessDataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            produceFile = { File(cacheDir, "myapp.preferences_pb") }
        )
    }

    companion object {
        lateinit var instance: AttenDApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@AttenDApp)
            androidLogger()
            modules(appModule)
        }
    }
}