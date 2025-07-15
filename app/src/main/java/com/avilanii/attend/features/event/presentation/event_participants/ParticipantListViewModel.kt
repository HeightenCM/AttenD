package com.avilanii.attend.features.event.presentation.event_participants

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avilanii.attend.core.domain.onError
import com.avilanii.attend.core.domain.onSuccess
import com.avilanii.attend.features.event.data.networking.datatransferobjects.CheckInConfirmationDTO
import com.avilanii.attend.features.event.domain.AttendeeTier
import com.avilanii.attend.features.event.domain.ParticipantDataSource
import com.avilanii.attend.features.event.presentation.models.toCSV
import com.avilanii.attend.features.event.presentation.models.toParticipantUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ParticipantListViewModel(
    private val participantDataSource: ParticipantDataSource,
    private val eventId: Int,
    private val contentResolver: ContentResolver
): ViewModel() {
    private val _state = MutableStateFlow(ParticipantListState())
    val state = _state
        .onStart {
            loadParticipants()
            loadEventTiers()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ParticipantListState()
        )

    private val _events = Channel<ParticipantListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ParticipantListAction){
        when(action) {
            is ParticipantListAction.OnAddParticipantClick -> _state.update {
                it.copy(isAddingParticipant = true)
            }
            is ParticipantListAction.OnAddedParticipant -> addParticipant(
                    name = action.name,
                    email = action.email
                )
            is ParticipantListAction.OnDismissAddParticipantDialog -> _state.update {
                it.copy(isAddingParticipant = false)
            }
            is ParticipantListAction.OnParticipantClick -> _state.update {
                it.copy(
                    isModifyingAttendeeTiers = true,
                    isAssigningTier = true,
                    selectedParticipant = action.participantUi
                )
            }
            is ParticipantListAction.OnMenuIconClick -> {}
            is ParticipantListAction.OnGenerateInviteQrDismissDialog -> {
                _state.update {
                    it.copy(
                        isShowingInviteQr = false,
                        inviteQr = null
                    )
                }
            }
            is ParticipantListAction.OnGenerateInviteQrOpenDialog -> generateQRInvite()
            is ParticipantListAction.OnScanQrClick -> scanQr(action.qrValue)
            is ParticipantListAction.OnDismissReviewCheckIn -> _state.update {
                it.copy(
                    isReviewingCheckIn = false,
                    checkInResponse = null
                )
            }
            is ParticipantListAction.OnDismissModifyEventTiersClick -> _state.update {
                it.copy(
                    isModifyingAttendeeTiers = false,
                    isAssigningTier = false,
                    selectedParticipant = null
                )
            }
            is ParticipantListAction.OnModifyEventTiersClick -> displayEventTiers()
            is ParticipantListAction.OnAddEventTierClick -> addEventTier(action.eventTier)
            is ParticipantListAction.OnRemoveEventTierClick -> removeEventTier(action.tierId)
            is ParticipantListAction.OnExportToCSVClick -> exportToCSV(action.uri)
            is ParticipantListAction.OnImportFromCSVClick -> importFromCSV(action.uri){ participants ->
                addParticipants(participants)
            }
            is ParticipantListAction.OnAssignParticipantTierClick ->
                assignParticipantTier(action.participantId, action.tierId)
            is ParticipantListAction.OnResignParticipantTierClick -> resignParticipantTier(action.participantId)
        }
    }

    private fun loadParticipants(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            participantDataSource
                .getParticipants(eventId)
                .onSuccess { participants ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            participants = participants.map { it.toParticipantUi() }
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(ParticipantListEvent.Error(error))
                }
        }
    }

    private fun addParticipant(name: String, email: String){
        viewModelScope.launch {
            participantDataSource
                .addParticipant(
                    eventId = eventId,
                    name = name,
                    email = email
                )
                .onSuccess { participantReceived ->
                    _state.update {
                        it.copy(
                            participants = it.participants + participantReceived.toParticipantUi()
                        )
                    }
                }
                .onError { error ->
                    _events.send(ParticipantListEvent.Error(error))
                }
        }
        _state.update {
            it.copy(isAddingParticipant = false)
        }
    }

    private fun addParticipants(participants: List<Pair<String, String>>){
        viewModelScope.launch {
            participantDataSource
                .addParticipants(eventId,participants)
                .onSuccess {  participants ->
                    _state.update {
                        it.copy(
                            participants = it.participants + participants.map { it.toParticipantUi() }
                        )
                    }
                }
                .onError { error ->
                    _events.send(ParticipantListEvent.Error(error))
                }
        }
    }

    private fun generateQRInvite(){
        viewModelScope.launch {
            participantDataSource
                .generateQRInvite(eventId)
                .onSuccess { receivedQR ->
                    _state.update {
                        it.copy(
                            inviteQr = receivedQR,
                            isShowingInviteQr = true
                        )
                    }
                }
                .onError { error ->
                    _events.send(ParticipantListEvent.Error(error))
                }
        }
    }

    private fun scanQr(qrCode: String){
        viewModelScope.launch {
            participantDataSource
                .scanParticipantQr(eventId, qrCode)
                .onSuccess { checkInResponse ->
                    _state.update {
                        it.copy(
                            checkInResponse = checkInResponse,
                            isReviewingCheckIn = true
                        )
                    }
                }
                .onError {
                    _state.update {
                        it.copy(
                            checkInResponse =
                                CheckInConfirmationDTO(
                                    message = "We couldn't find the QR for this participant!",
                                    accepted = false
                                ),
                            isReviewingCheckIn = true
                        )
                    }
                }
        }
    }

    private fun loadEventTiers(){
        viewModelScope.launch {
            participantDataSource
                .getEventTiers(eventId)
                .onSuccess {  tiers ->
                    _state.update {
                        it.copy(
                            eventTiers = tiers
                        )
                    }
                }
                .onError {  error ->
                    _events.send(ParticipantListEvent.Error(error))
                }
        }
    }

    private fun displayEventTiers(){
        _state.update {
            it.copy(
                isModifyingAttendeeTiers = true
            )
        }
    }

    private fun addEventTier(eventTier: String){
        viewModelScope.launch {
            participantDataSource
                .addEventTier(
                    attendeeTier = eventTier,
                    eventId = eventId
                )
                .onSuccess { receivedTierId ->
                    _state.update {
                        it.copy(
                            eventTiers = it.eventTiers + AttendeeTier(receivedTierId, eventTier)
                        )
                    }
                }
                .onError {
                    _state.update {
                        it.copy(
                            isModifyingAttendeeTiers = false
                        )
                    }
                }
        }
    }

    private fun removeEventTier(tierId: Int){
        viewModelScope.launch {
            participantDataSource
                .removeEventTier(
                    tierId = tierId,
                    eventId = eventId
                )
                .onSuccess {
                    _state.update {
                        it.copy(
                            eventTiers = it.eventTiers.filterNot { tier -> tier.id == tierId },
                            participants = it.participants.map { participantUi ->
                                if(participantUi.tier.id == tierId){
                                    participantUi.copy(tier = AttendeeTier(-1,"Unassigned"))
                                } else participantUi
                            }
                        )
                    }
                }
                .onError {
                    _state.update {
                        it.copy(
                            isModifyingAttendeeTiers = false
                        )
                    }
                }
        }
    }

    private fun assignParticipantTier(participantId: Int, tierId: Int){
        viewModelScope.launch {
            participantDataSource
                .assignParticipantTier(
                    eventId = eventId,
                    participantId = participantId,
                    tierId = tierId
                )
                .onSuccess { tierName ->
                    _state.update {
                        it.copy(
                            participants = it.participants.map { participantUi ->
                                if (participantUi.id == participantId)
                                    participantUi.copy(
                                        tier = AttendeeTier(tierId, tierName)
                                    )
                                else
                                    participantUi
                            },
                            isAssigningTier = false,
                            isModifyingAttendeeTiers = false,
                            selectedParticipant = null
                        )
                    }
                }
                .onError { error ->
                    _events.send(ParticipantListEvent.Error(error))
                    _state.update {
                        it.copy(
                            isAssigningTier = false,
                            isModifyingAttendeeTiers = false,
                            selectedParticipant = null
                        )
                    }
                }
        }
    }

    private fun resignParticipantTier(participantId: Int){
        viewModelScope.launch {
            participantDataSource
                .resignParticipantTier(
                    eventId = eventId,
                    participantId = participantId
                )
                .onSuccess {
                    _state.update {
                        it.copy(
                            participants = it.participants.map { participantUi ->
                                if(participantUi.id == participantId)
                                    participantUi.copy(tier = AttendeeTier(-1,"Unassigned"))
                                else
                                    participantUi
                            },
                            isAssigningTier = false,
                            isModifyingAttendeeTiers = false,
                            selectedParticipant = null
                        )
                    }
                }
                .onError { error ->
                    _events.send(ParticipantListEvent.Error(error))
                    _state.update {
                        it.copy(
                            isAssigningTier = false,
                            isModifyingAttendeeTiers = false,
                            selectedParticipant = null
                        )
                    }
                }
        }
    }

    private fun exportToCSV(uri: Uri){
        viewModelScope.launch(Dispatchers.IO) {
            contentResolver.openOutputStream(uri)?.use {
                it.bufferedWriter().use {
                    it.write(_state.value.participants.toCSV())
                }
            } ?: throw IllegalStateException("Cannot open output stream for $uri")
        }
    }

    private fun importFromCSV(uri: Uri, onResult: (List<Pair<String, String>>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val participants = mutableListOf<Pair<String, String>>()

            try {
                contentResolver.openInputStream(uri)?.use { inputStream ->
                    inputStream.bufferedReader().useLines { lines ->
                        val iterator = lines.iterator()
                        if (!iterator.hasNext()) return@useLines

                        val header = iterator.next().split(",").map { it.trim() }

                        val nameIndex = header.indexOfFirst { it.equals("name", ignoreCase = true) }
                        val emailIndex = header.indexOfFirst { it.equals("email", ignoreCase = true) }

                        if (nameIndex == -1 || emailIndex == -1) return@useLines

                        iterator.forEachRemaining { line ->
                            val fields = line.split(",").map { it.trim() }

                            if (fields.size > maxOf(nameIndex, emailIndex)) {
                                val name = fields[nameIndex]
                                val email = fields[emailIndex]

                                if (name.isNotBlank() && email.isNotBlank()) {
                                    participants.add(Pair(first = name, second = email))
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            withContext(Dispatchers.Main) {
                onResult(participants)
            }
        }
    }

}