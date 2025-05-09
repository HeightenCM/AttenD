package com.avilanii.attend.core.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EventMenuModalDrawer(
    selectedIndex: Int,
    onPageSelected: (Int) -> Unit,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                EventMenuItems.entries.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(item.title)
                        },
                        selected = index == selectedIndex,
                        onClick = { onPageSelected(index) },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedIndex)
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
    ) { content }
}