package com.example.imatah.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.imatah.presentation.view.MainScreen
import com.example.imatah.presentation.view.components.AddReportScreen
import com.example.imatah.presentation.viewmodel.AddReportViewModel
import com.example.imatah.presentation.viewmodel.CategoryViewModel
import com.example.imatah.presentation.viewmodel.ReportViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    categoryViewModel: CategoryViewModel,
    reportViewModel: ReportViewModel
) {
    val addReportViewModel: AddReportViewModel = hiltViewModel()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                navController = navController,
                categoryViewModel = categoryViewModel,
                reportViewModel = reportViewModel
            )
        }
        composable("add_report") {
            AddReportScreen(
                navController = navController,
                viewModel = addReportViewModel,
                context = context
            )
        }
    }
}
