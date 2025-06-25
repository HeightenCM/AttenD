package com.avilanii.attend.features.account.presentation.accountmenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.avilanii.attend.core.navigation.BottomNavigationItem
import com.avilanii.attend.features.account.presentation.models.User
import com.avilanii.attend.ui.theme.AttenDTheme

@Composable
fun AccountMenuScreen(
    modifier: Modifier = Modifier,
    state: AccountMenuState,
    bottomNavBarItems: List<BottomNavigationItem>,
    onAction: (AccountMenuAction)->Unit
    ) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavBarItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = index == 2,
                        onClick = {onAction(AccountMenuAction.OnNavigateClick(index))},
                        icon = {
                            Icon(
                                imageVector = if (index == 2)
                                    item.selectedIcon
                                else
                                    item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        label = {Text(item.title)}
                    )
                }
            }
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Account Icon",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(bottom = 8.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = state.loggedUser?.name ?: "Failed to load",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = state.loggedUser?.email ?: "Failed to load",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { onAction(AccountMenuAction.OnLogOutClick) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Text(text = "Log out")
                }
            }
        }
    }

}

@PreviewLightDark
@Composable
private fun PreviewAccountMenuScreen() {
    AttenDTheme {
        AccountMenuScreen(
            state = AccountMenuState(
                loggedUser = User("John Doe", "johndoe@gmail.com")
            ),
            bottomNavBarItems = listOf(
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
            ),
            onAction = {}
        )
    }
}