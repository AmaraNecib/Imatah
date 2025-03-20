package com.example.imatah.presentation.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.imatah.presentation.view.components.AddReportScreen
import com.example.imatah.presentation.view.components.ImatahTopBar
import com.example.imatah.presentation.view.components.MainScreen
import com.example.imatah.presentation.viewmodel.CategoryViewModel
import com.example.imatah.presentation.viewmodel.ReportViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "mainScreen"
    ) {
        composable("mainScreen") {

            val categoryViewModel: CategoryViewModel = hiltViewModel()
            val reportViewModel: ReportViewModel = hiltViewModel()

            Scaffold(
                topBar = {
                    ImatahTopBar(navController) // تمرير navController إلى ImatahTopBar
                }
            ) { paddingValues -> // ✅ استخدام `paddingValues` في `Scaffold`
                MainScreen(
                    navController = navController,
                    categoryViewModel = categoryViewModel,
                    reportViewModel = reportViewModel,
                    modifier = Modifier.padding(paddingValues), // ✅ تطبيق `padding`
                    onNavigateToAddReport = {
                        navController.navigate("addReportScreen")
                    }
                )
            }
        }

        composable("addReportScreen") {
            AddReportScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
