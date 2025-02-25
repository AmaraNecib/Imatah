package com.example.imatah.view.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ImatahBottomNavigation(
    currentRoute: String,
    onRouteSelected: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .shadow(8.dp, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(24.dp)),
        color = Color.Gray
    ) {
        NavigationBar(
            containerColor = Color.White,
        ) {
            NavigationBarItem(
                selected = currentRoute == "Home",
                onClick = { onRouteSelected("Home") },
                label = { Text(text = "Home", color = Color.Black) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            )
            NavigationBarItem(
                selected = currentRoute == "Map",
                onClick = { onRouteSelected("Map") },
                label = { Text(text = "Map", color = Color.Black) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            )
            NavigationBarItem(
                selected = currentRoute == "Search",
                onClick = { onRouteSelected("Search") },
                label = { Text(text = "Search", color = Color.Black) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            )
            NavigationBarItem(
                selected = currentRoute == "Quick-Report",
                onClick = { onRouteSelected("Quick-Report") },
                label = { Text(text = "Quick-Report", maxLines = 1, color = Color.Black) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            )
            NavigationBarItem(
                selected = currentRoute == "Account",
                onClick = { onRouteSelected("Account") },
                label = { Text(text = "Account", color = Color.Black) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            )
        }
    }
}
