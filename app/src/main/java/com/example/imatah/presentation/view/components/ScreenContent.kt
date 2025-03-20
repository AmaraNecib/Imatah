package com.example.imatah.presentation.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
//import com.example.imatah.presentation.view.MainScreen
import com.example.imatah.presentation.viewmodel.CategoryViewModel
import com.example.imatah.presentation.viewmodel.ReportViewModel

@Composable
fun ScreenContent(
    navController: NavHostController,
    currentRoute: String,
    categoryViewModel: CategoryViewModel,
    reportViewModel: ReportViewModel,
    modifier: Modifier = Modifier,
    onAddReportClick: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (currentRoute) {
            "Home" ->
                MainScreen(
                    navController = navController,
                    categoryViewModel = categoryViewModel,
                    reportViewModel = reportViewModel,
                    modifier = Modifier.padding(16.dp),
                    onNavigateToAddReport = {
                        navController.navigate("addReportScreen")
                    }
                )
            "Account" -> Text(text = "Account", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            "Map" -> Text(text = "Map", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            "Search" -> Text(text = "Search", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            "Quick-Report" -> Text(text = "Quick-Report", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            else -> Text(text = "Unknown", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        }
    }
}