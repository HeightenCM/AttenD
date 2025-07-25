package com.avilanii.attend.core.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.avilanii.attend.AttenDApp
import com.avilanii.attend.SessionManager
import com.avilanii.attend.core.data.UserPreferences
import com.avilanii.attend.core.domain.NetworkError
import com.avilanii.attend.core.domain.onError
import com.avilanii.attend.core.domain.onSuccess
import com.avilanii.attend.core.presentation.ObserveAsEvents
import com.avilanii.attend.core.presentation.toString
import com.avilanii.attend.features.account.presentation.accountmenu.AccountMenuAction
import com.avilanii.attend.features.account.presentation.accountmenu.AccountMenuEvent
import com.avilanii.attend.features.account.presentation.accountmenu.AccountMenuScreen
import com.avilanii.attend.features.account.presentation.accountmenu.AccountMenuViewModel
import com.avilanii.attend.features.auth.domain.AuthDataSource
import com.avilanii.attend.features.auth.presentation.login.LoginScreen
import com.avilanii.attend.features.auth.presentation.login.LoginScreenAction
import com.avilanii.attend.features.auth.presentation.register.RegisterScreen
import com.avilanii.attend.features.auth.presentation.register.RegisterScreenAction
import com.avilanii.attend.features.event.presentation.attending_events.AttendingEventListEvent
import com.avilanii.attend.features.event.presentation.attending_events.AttendingEventsListAction
import com.avilanii.attend.features.event.presentation.attending_events.AttendingEventsListScreen
import com.avilanii.attend.features.event.presentation.attending_events.AttendingEventsListViewModel
import com.avilanii.attend.features.event.presentation.event_analytics.EventAnalyticsEvent
import com.avilanii.attend.features.event.presentation.event_analytics.EventAnalyticsScreen
import com.avilanii.attend.features.event.presentation.event_analytics.EventAnalyticsScreenAction
import com.avilanii.attend.features.event.presentation.event_analytics.EventAnalyticsScreenViewModel
import com.avilanii.attend.features.event.presentation.event_announcements.EventAnnouncementsEvent
import com.avilanii.attend.features.event.presentation.event_announcements.EventAnnouncementsScreen
import com.avilanii.attend.features.event.presentation.event_announcements.EventAnnouncementsScreenAction
import com.avilanii.attend.features.event.presentation.event_announcements.EventAnnouncementsScreenViewModel
import com.avilanii.attend.features.event.presentation.event_iot.EventIotEvent
import com.avilanii.attend.features.event.presentation.event_iot.EventIotScreen
import com.avilanii.attend.features.event.presentation.event_iot.EventIotScreenAction
import com.avilanii.attend.features.event.presentation.event_iot.EventIotScreenViewModel
import com.avilanii.attend.features.event.presentation.event_list.EventListAction
import com.avilanii.attend.features.event.presentation.event_list.EventListEvent
import com.avilanii.attend.features.event.presentation.event_list.EventListScreen
import com.avilanii.attend.features.event.presentation.event_list.EventListViewModel
import com.avilanii.attend.features.event.presentation.event_participants.ParticipantListAction
import com.avilanii.attend.features.event.presentation.event_participants.ParticipantListEvent
import com.avilanii.attend.features.event.presentation.event_participants.ParticipantListScreen
import com.avilanii.attend.features.event.presentation.event_participants.ParticipantListViewModel
import kotlinx.coroutines.flow.first
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
    val scope = rememberCoroutineScope()
    val dataStore = AttenDApp.instance.userPrefsStore

    LaunchedEffect(SessionManager.jwtToken) {
        SessionManager.setToken(dataStore.data.first().jwt)
        if (SessionManager.jwtToken != null) {
            navController.navigate(Home) {
                popUpTo(Auth) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Auth
    ) {
        navigation<Auth>(
            startDestination = Login("", "")
        ) {
            composable<Login>{
                val context = LocalContext.current
                val args = it.toRoute<Login>()
                val authDataSource = koinInject<AuthDataSource>()
                val coroutineScope = rememberCoroutineScope()
                LoginScreen(emailReceived = args.email, passwordReceived = args.password){ action ->
                    when (action){
                        is LoginScreenAction.OnSubmitForm -> {
                            coroutineScope.launch {
                                authDataSource.login(action.userLoginRequestUi.email, action.userLoginRequestUi.password)
                                    .onSuccess { jwtReceived ->
                                        scope.launch {
                                            dataStore.updateData {
                                                UserPreferences(
                                                    jwt = jwtReceived
                                                )
                                            }
                                        }
                                        SessionManager.setToken(jwtReceived)
                                        navController.navigate(Home){
                                            popUpTo(Auth) {
                                                inclusive = true
                                            }
                                        }
                                    }
                                    .onError { error ->
                                        if (error == NetworkError.BAD_REQUEST)
                                        Toast.makeText(
                                            context,
                                            "Invalid credentials!",
                                            Toast.LENGTH_LONG
                                        ).show()
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
                val context = LocalContext.current
                val authDataSource = koinInject<AuthDataSource>()
                val coroutineScope = rememberCoroutineScope()
                RegisterScreen{ action ->
                    when (action){
                        is RegisterScreenAction.OnSubmitForm -> {
                            val name = action.userRegisterRequestUi.name
                            val email = action.userRegisterRequestUi.email
                            val password = action.userRegisterRequestUi.password
                            coroutineScope.launch {
                                authDataSource.register(name, email, password)
                                    .onSuccess {
                                        navController.navigate(Login(email, password)){
                                            popUpTo(Auth){
                                                inclusive = true
                                            }
                                        }
                                    }
                                    .onError {error ->
                                        if (error == NetworkError.CONFLICT){
                                            Toast.makeText(
                                                context,
                                                "Account already exists with that email!",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                            }
                        }
                        is RegisterScreenAction.OnLoginClick -> {
                            navController.navigate(Login("", "")){
                                popUpTo(Auth){
                                    inclusive = true
                                }
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
                    selectedIcon = Icons.Filled.Event,
                    unselectedIcon = Icons.Outlined.Event
                ),
                BottomNavigationItem(
                    title = "Attending",
                    selectedIcon = Icons.Filled.EventAvailable,
                    unselectedIcon = Icons.Outlined.EventAvailable
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
                            navController.navigate(EventManagement(action.eventUi.id))
                        }
                        is EventListAction.OnCreateEventClick -> {}
                        is EventListAction.OnCreatedEvent -> {}
                        is EventListAction.OnDismissCreateEventDialog -> {}
                        is EventListAction.OnNavigateClick -> {
                            when (action.index){
                                1 -> navController.navigate(AttendingEventsList){
                                    popUpTo(Home) {
                                        inclusive = true
                                    }
                                }
                                2 -> navController.navigate(AccountMenu) {
                                    popUpTo(Home) {
                                        inclusive = true
                                    }
                                }
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
                        is AttendingEventsListAction.OnDismissEventInspectDialog -> {}
                        is AttendingEventsListAction.OnEventClick -> {}
                        is AttendingEventsListAction.OnNavigateClick -> {
                            when (action.index){
                                0 -> navController.navigate(EventList){
                                    popUpTo(Home) {
                                        inclusive = true
                                    }
                                }
                                2 -> navController.navigate(AccountMenu){
                                    popUpTo(Home){
                                        inclusive = true
                                    }
                                }
                            }
                        }
                        is AttendingEventsListAction.OnRejectEventInvitationClick -> {}
                        is AttendingEventsListAction.OnSaveExternalQrClick -> {}
                        is AttendingEventsListAction.OnDismissSaveExternalQrClick -> {}
                        is AttendingEventsListAction.OnExternalEventClick -> {}
                        is AttendingEventsListAction.OnDismissViewAnnouncements -> {}
                        is AttendingEventsListAction.OnViewAnnouncementsClick -> {}
                    }
                }
            }
            composable<AccountMenu>{
                val viewModel: AccountMenuViewModel = koinViewModel<AccountMenuViewModel>()
                val state by viewModel.state.collectAsStateWithLifecycle()
                val context = LocalContext.current
                val coroutineScope = rememberCoroutineScope()

                ObserveAsEvents(events = viewModel.events) { event ->
                    when (event) {
                        is AccountMenuEvent.Error -> {
                            Toast.makeText(
                                context,
                                event.error.toString(context),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                AccountMenuScreen(
                    modifier = modifier,
                    state = state,
                    bottomNavBarItems = bottomNavBarItems
                ) { action ->
                    viewModel.onAction(action)
                    when(action){
                        is AccountMenuAction.OnLogOutClick -> {
                            coroutineScope.launch {
                                dataStore.updateData {
                                    UserPreferences(
                                        jwt = null
                                    )
                                }
                            }
                            SessionManager.clearToken()
                            navController.navigate(Login("", "")){
                                popUpTo(Auth) {
                                    inclusive = true
                                }
                            }
                        }
                        is AccountMenuAction.OnNavigateClick -> {
                            when(action.index){
                                0 -> navController.navigate(EventList) {
                                    popUpTo(Home) {
                                        inclusive = true
                                    }
                                }
                                1 -> navController.navigate(AttendingEventsList) {
                                    popUpTo(Home) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
            composable<EventManagement>{
                val newNavController = rememberNavController()
                val eventId = it.arguments?.getInt("eventId")
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
                ModalNavigationDrawer(
                    drawerContent = {
                        ModalDrawerSheet {
                            EventMenuItems.entries.forEachIndexed { index, item ->
                                NavigationDrawerItem(
                                    label = {
                                        Text(item.title)
                                    },
                                    selected = index == selectedItemIndex,
                                    onClick = {
                                        selectedItemIndex = index
                                        scope.launch {
                                            drawerState.close()
                                        }
                                        newNavController.navigate(item.route){
                                            popUpTo(EventManagementSubgraph){
                                                inclusive = true
                                            }
                                        }
                                              },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedItemIndex)
                                                item.selectedIcon
                                            else item.unselectedIcon,
                                            contentDescription = item.title
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    },
                    drawerState = drawerState
                ){
                    NavHost(
                        navController = newNavController,
                        startDestination = EventManagementSubgraph
                    ) {
                        navigation<EventManagementSubgraph>(
                            startDestination = EventMenuRoutes.Participants
                        ){
                            composable<EventMenuRoutes.Participants>{
                                val viewModel: ParticipantListViewModel = koinViewModel<ParticipantListViewModel>(parameters = {parametersOf(eventId)})
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
                                    when(action){
                                        is ParticipantListAction.OnAddParticipantClick -> {}
                                        is ParticipantListAction.OnAddedParticipant -> {}
                                        is ParticipantListAction.OnDismissAddParticipantDialog -> {}
                                        is ParticipantListAction.OnMenuIconClick -> {
                                            scope.launch {
                                                drawerState.apply { if (isClosed) open() else close() }
                                            }
                                        }
                                        is ParticipantListAction.OnParticipantClick -> {}
                                        is ParticipantListAction.OnGenerateInviteQrDismissDialog -> {}
                                        is ParticipantListAction.OnGenerateInviteQrOpenDialog -> {}
                                        is ParticipantListAction.OnScanQrClick -> {}
                                        is ParticipantListAction.OnDismissReviewCheckIn -> {}
                                        is ParticipantListAction.OnDismissModifyEventTiersClick -> {}
                                        is ParticipantListAction.OnModifyEventTiersClick -> {}
                                        is ParticipantListAction.OnRemoveEventTierClick -> {}
                                        is ParticipantListAction.OnAddEventTierClick -> {}
                                        is ParticipantListAction.OnExportToCSVClick -> {}
                                        is ParticipantListAction.OnImportFromCSVClick -> {}
                                        is ParticipantListAction.OnAssignParticipantTierClick -> {}
                                        is ParticipantListAction.OnResignParticipantTierClick -> {}
                                    }
                                }
                            }

                            composable<EventMenuRoutes.Analytics>{
                                val viewModel: EventAnalyticsScreenViewModel =
                                    koinViewModel<EventAnalyticsScreenViewModel>(parameters = {
                                        parametersOf(eventId)
                                    })
                                val state by viewModel.state.collectAsStateWithLifecycle()
                                val context = LocalContext.current

                                ObserveAsEvents(events = viewModel.events) { event ->
                                    when (event) {
                                        is EventAnalyticsEvent.Error -> {
                                            Toast.makeText(
                                                context,
                                                event.error.toString(context),
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }

                                EventAnalyticsScreen(
                                    modifier = modifier,
                                    state = state
                                ) { action ->
                                    viewModel.onAction(action)
                                    when (action){
                                        is EventAnalyticsScreenAction.OnMenuIconClick -> {
                                            scope.launch {
                                                drawerState.apply { if (isClosed) open() else close() }
                                            }
                                        }
                                        is EventAnalyticsScreenAction.OnTierDistributionPieClick -> {}
                                        is EventAnalyticsScreenAction.OnParticipantStatusDistributionClick -> {}
                                    }
                                }
                            }

                            composable<EventMenuRoutes.IoT> {
                                val viewModel: EventIotScreenViewModel =
                                    koinViewModel<EventIotScreenViewModel>(parameters = {
                                        parametersOf(eventId)
                                    })
                                val state by viewModel.state.collectAsStateWithLifecycle()
                                val context = LocalContext.current

                                ObserveAsEvents(events = viewModel.events) { event ->
                                    when (event) {
                                        is EventIotEvent.Error -> {
                                            Toast.makeText(
                                                context,
                                                event.error.toString(context),
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }

                                EventIotScreen(
                                    state = state,
                                    modifier = modifier
                                ) { action ->
                                    viewModel.onAction(action)
                                    when (action) {
                                        is EventIotScreenAction.OnMenuIconClick -> {
                                            scope.launch {
                                                drawerState.apply { if (isClosed) open() else close() }
                                            }
                                        }
                                        is EventIotScreenAction.OnAddGateClick -> {}
                                        is EventIotScreenAction.OnChangeTierStateClick -> {}
                                        is EventIotScreenAction.OnCreateGateClick -> {}
                                        is EventIotScreenAction.OnDismissActivateGateClick -> {}
                                        is EventIotScreenAction.OnDismissAddGateClick -> {}
                                        is EventIotScreenAction.OnDismissGateTierDialog -> {}
                                        is EventIotScreenAction.OnGateClick -> {}
                                    }
                                }
                            }

                            composable<EventMenuRoutes.Announcements>{
                                val viewModel: EventAnnouncementsScreenViewModel =
                                    koinViewModel<EventAnnouncementsScreenViewModel>(parameters = {
                                        parametersOf(eventId)
                                    })
                                val state by viewModel.state.collectAsStateWithLifecycle()
                                val context = LocalContext.current

                                ObserveAsEvents(events = viewModel.events) { event ->
                                    when (event) {
                                        is EventAnnouncementsEvent.Error -> {
                                            Toast.makeText(
                                                context,
                                                event.error.toString(context),
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }

                                EventAnnouncementsScreen(
                                    modifier = modifier,
                                    state = state
                                ) { action ->
                                    viewModel.onAction(action)
                                    when (action){
                                        is EventAnnouncementsScreenAction.OnDismissPostAnnouncementDialog -> {}
                                        is EventAnnouncementsScreenAction.OnMenuIconClick -> {
                                            scope.launch {
                                                drawerState.apply { if (isClosed) open() else close() }
                                            }
                                        }
                                        is EventAnnouncementsScreenAction.OnPostAnnouncementClick -> {}
                                        is EventAnnouncementsScreenAction.OnPostedAnnouncementClick -> {}
                                    }
                                }
                            }
                        }
                    }
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
data object AccountMenu
@Serializable
data object Home
@Serializable
data class Login(val email: String, val password: String)
@Serializable
data object Register
@Serializable
data object Auth
@Serializable
data class EventManagement(val eventId: Int)
@Serializable
data object EventManagementSubgraph