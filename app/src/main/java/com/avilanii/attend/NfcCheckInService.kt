package com.avilanii.attend

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import com.avilanii.attend.core.data.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class NfcCheckInService : HostApduService() {
    lateinit var datastore: DataStore<UserPreferences>

    companion object {
        const val CONFIG_AID = "F222222223"
        const val NORMAL_AID = "F222222222"
        const val ERROR_MARKER   = "FF FF"
    }

    override fun onCreate() {
        super.onCreate()
        datastore = AttenDApp.instance.userPrefsStore
    }

    override fun processCommandApdu(
        commandApdu: ByteArray?,
        extras: Bundle?
    ): ByteArray? {
        val apduHex = commandApdu?.joinToString("") { "%02X".format(it) } ?: ""
        val pair = runBlocking {
            Pair(datastore.data.first().participantIdentifier, datastore.data.first().gateIdentifier)
        }
        Log.wtf("JWT", pair.first)
        Log.wtf("eventID", pair.second.toString())
        return when {
            apduHex.contains(CONFIG_AID) ->
                pair.second?.toByteArray(Charsets.UTF_8) ?: ERROR_MARKER.toByteArray(Charsets.UTF_8)

            apduHex.contains(NORMAL_AID) ->
                pair.first?.toByteArray(Charsets.UTF_8) ?: ERROR_MARKER.toByteArray(Charsets.UTF_8)

            else -> ERROR_MARKER.toByteArray(Charsets.UTF_8)
        }
    }

    override fun onDeactivated(reason: Int) {
    }
}