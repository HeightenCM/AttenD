package com.avilanii.attend

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.core.MultiProcessDataStoreFactory
import com.avilanii.attend.core.data.UserPreferences
import com.avilanii.attend.core.data.UserPreferencesSerializer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.io.File

class NfcCheckInService : HostApduService() {
    private lateinit var dataStore: DataStore<UserPreferences>

    override fun onCreate() {
        super.onCreate()
        dataStore = MultiProcessDataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            produceFile = {
                File(cacheDir, "myapp.preferences_pb")
            }
        )
    }

    override fun processCommandApdu(
        commandApdu: ByteArray?,
        extras: Bundle?
    ): ByteArray? {
        return try {
            val jwt = runBlocking {
                dataStore.data.first().jwt
            }
            jwt?.toByteArray(Charsets.UTF_8)
        } catch (e: Exception) {
            null
        }
    }

    override fun onDeactivated(reason: Int) {
        TODO("Not yet implemented")
    }
}