package com.avilanii.attend.core.navigation

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.avilanii.attend.core.domain.onError
import com.avilanii.attend.core.domain.onSuccess
import com.avilanii.attend.core.presentation.ObserveAsEvents
import com.avilanii.attend.core.presentation.toString
import com.avilanii.attend.features.auth.domain.AuthDataSource
import com.avilanii.attend.features.auth.presentation.login.LoginScreen
import com.avilanii.attend.features.auth.presentation.login.LoginScreenAction
import com.avilanii.attend.features.auth.presentation.register.RegisterScreen
import com.avilanii.attend.features.auth.presentation.register.RegisterScreenAction
import com.avilanii.attend.features.event.presentation.attending_events.AttendingEventListEvent
import com.avilanii.attend.features.event.presentation.attending_events.AttendingEventsListAction
import com.avilanii.attend.features.event.presentation.attending_events.AttendingEventsListScreen
import com.avilanii.attend.features.event.presentation.attending_events.AttendingEventsListViewModel
import com.avilanii.attend.features.event.presentation.event_list.EventListAction
import com.avilanii.attend.features.event.presentation.event_list.EventListEvent
import com.avilanii.attend.features.event.presentation.event_list.EventListScreen
import com.avilanii.attend.features.event.presentation.event_list.EventListViewModel
import com.avilanii.attend.features.event.presentation.event_participants.ParticipantListEvent
import com.avilanii.attend.features.event.presentation.event_participants.ParticipantListScreen
import com.avilanii.attend.features.event.presentation.event_participants.ParticipantListViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun ApplicationRootComposable(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    LaunchedEffect(SessionManager.jwtToken.collectAsState().value) {
        if (SessionManager.jwtToken.value == null) {
            navController.navigate(Auth) {
                popUpTo(Home) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        navigation<Auth>(
            startDestination = Login("", "")
        ) {
            composable<Login>{
                val args = it.toRoute<Login>()
                val authDataSource = koinInject<AuthDataSource>()
                val coroutineScope = rememberCoroutineScope()
                LoginScreen(emailReceived = args.email, passwordReceived = args.password){ action ->
                    when (action){
                        is LoginScreenAction.OnSubmitForm -> {
                            coroutineScope.launch {
                                authDataSource.login(action.userLoginRequestUi.email, action.userLoginRequestUi.password)
                                    .onSuccess { jwtReceived ->
                                        SessionManager.setToken(jwtReceived)
                                        navController.navigate(Home){
                                            popUpTo(Auth) {
                                                inclusive = true
                                            }
                                        }
                                    }
                                    .onError {

                                    }
                            }
                        }
                        is LoginScreenAction.OnRegisterClick -> {
                            navController.navigate(Register)
                        }
                    }
                }
            }
            composable<Register> {
                val authDataSource = koinInject<AuthDataSource>()
                val coroutineScope = rememberCoroutineScope()
                RegisterScreen(){ action ->
                    when (action){
                        is RegisterScreenAction.OnSubmitForm -> {
                            val name = action.userRegisterRequestUi.name
                            val email = action.userRegisterRequestUi.email
                            val password = action.userRegisterRequestUi.password
                            coroutineScope.launch {
                                authDataSource.register(name, email, password)
                                    .onSuccess {
                                        navController.navigate(Login(email, password)){
                                            popUpTo(Auth)
                                        }
                                    }
                                    .onError {

                                    }
                            }
                        }
                        is RegisterScreenAction.OnLoginClick -> {
                            navController.navigate(Login("", "")){
                                popUpTo(Auth)
                            }
                        }
                    }
                }
            }
        }
        navigation<Home>(
            startDestination = EventList
        ) {
            val bottomNavBarItems = listOf(
                BottomNavigationItem(
                    title = "Organizing",
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home
                ),
                BottomNavigationItem(
                    title = "Attending",
                    selectedIcon = Icons.Filled.Email,
                    unselectedIcon = Icons.Outlined.Email
                ),
                BottomNavigationItem(
                    title = "Account",
                    selectedIcon = Icons.Filled.AccountCircle,
                    unselectedIcon = Icons.Outlined.AccountCircle
                ),
            )
            composable<EventList> {
                val viewModel: EventListViewModel = koinViewModel<EventListViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                val context = LocalContext.current

                ObserveAsEvents(events = viewModel.events) { event ->
                    when (event) {
                        is EventListEvent.Error -> {
                            Toast.makeText(
                                context,
                                event.error.toString(context),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                EventListScreen(
                    modifier = modifier,
                    state = state,
                    bottomNavBarItems = bottomNavBarItems
                ) { action ->
                    viewModel.onAction(action)
                    when (action){
                        is EventListAction.OnEventClick -> {
                            navController.navigate(Participants(action.eventUi.id))
                        }
                        is EventListAction.OnCreateEventClick -> {}
                        is EventListAction.OnCreatedEvent -> {}
                        is EventListAction.OnDismissCreateEventDialog -> {}
                        is EventListAction.OnNavigateClick -> {
                            when (action.index){
                                0 -> {}
                                1 -> navController.navigate(AttendingEventsList)
                                2 -> {TODO("Account management")}
                            }
                        }
                    }
                }
            }
            composable<AttendingEventsList>{
                val viewModel: AttendingEventsListViewModel = koinViewModel<AttendingEventsListViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                val context = LocalContext.current

                ObserveAsEvents(events = viewModel.events) { event ->
                    when (event) {
                        is AttendingEventListEvent.Error -> {
                            Toast.makeText(
                                context,
                                event.error.toString(context),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                AttendingEventsListScreen(
                    modifier = modifier,
                    state = state,
                    bottomNavBarItems = bottomNavBarItems
                ) { action ->
                    viewModel.onAction(action)
                    when (action){
                        is AttendingEventsListAction.OnAcceptEventInvitationClick -> {}
                        is AttendingEventsListAction.OnAddEventQrClick -> {}
                        is AttendingEventsListAction.OnDismissAddEventQrDialog -> {}
                        is AttendingEventsListAction.OnDismissEventInspectDialog -> {}
                        is AttendingEventsListAction.OnEventClick -> {}
                        is AttendingEventsListAction.OnNavigateClick -> {
                            when (action.index){
                                0 -> navController.navigate(EventList)
                                1 -> {}
                                2 -> {TODO("Account management")}
                            }
                        }
                        is AttendingEventsListAction.OnRejectEventInvitationClick -> {}
                    }
                }
            }

            composable<Participants>{
                val args = it.toRoute<Participants>()
                val viewModel: ParticipantListViewModel = koinViewModel<ParticipantListViewModel>(parameters = {parametersOf(args.eventId)})
                val state by viewModel.state.collectAsStateWithLifecycle()
                val context = LocalContext.current

                ObserveAsEvents(events = viewModel.events) { event ->
                    when (event) {
                        is ParticipantListEvent.Error -> {
                            Toast.makeText(
                                context,
                                event.error.toString(context),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                ParticipantListScreen(
                    state = state,
                    modifier = modifier
                ) { action ->
                    viewModel.onAction(action)
                }
            }
        }
    }
}

@Serializable
data object EventList
@Serializable
data object AttendingEventsList
@Serializable
data object Home
@Serializable
data class Login(val email: String, val password: String)
@Serializable
data object Register
@Serializable
data object Auth
@Serializable
data class Participants(val eventId: Int)

object SessionManager {
    private val _jwtToken = MutableStateFlow<String?>(null)
    val jwtToken: StateFlow<String?> = _jwtToken.asStateFlow()

    fun setToken(token: String) {
        _jwtToken.value = token
    }

    fun clearToken() {
        _jwtToken.value = null
    }
}