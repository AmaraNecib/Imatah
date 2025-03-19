package com.example.imatah.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.imatah.presentation.view.AddReportScreen
import com.example.imatah.presentation.view.MainScreen
import com.example.imatah.presentation.viewmodel.CategoryViewModel
import com.example.imatah.presentation.viewmodel.ReportViewModel

object Destinations {
    const val MAIN_SCREEN = "main_screen"
    const val ADD_REPORT_SCREEN = "add_report_screen"
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    categoryViewModel: CategoryViewModel,
    reportViewModel: ReportViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.MAIN_SCREEN,
        modifier = modifier
    ) {
        composable(Destinations.MAIN_SCREEN) {
            MainScreen(
                categoryViewModel = categoryViewModel,
                reportViewModel = reportViewModel,
                modifier = modifier,
                onAddDamagedRoadClick = {
                    navController.navigate(Destinations.ADD_REPORT_SCREEN)
                }
            )
        }

        composable(Destinations.ADD_REPORT_SCREEN) {
            AddReportScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
