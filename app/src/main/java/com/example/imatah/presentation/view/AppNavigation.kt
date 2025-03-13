package com.example.imatah.presentation.view

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.imatah.presentation.view.components.AddReportScreen
import com.example.imatah.presentation.viewmodel.CategoryViewModel
import com.example.imatah.presentation.viewmodel.ReportViewModel
//import com.example.imatah.presentation.view.MainScreen
import com.example.imatah.presentation.view.components.MainScreen

@Composable
fun AppNavigation() {
    // أنشئ navController لإدارة التنقل بين الشاشات
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "mainScreen"
    ) {
        // الشاشة الرئيسية
        composable("mainScreen") {
            // استدعاء الـ ViewModels من خلال Hilt♥
            val categoryViewModel: CategoryViewModel = hiltViewModel()
            val reportViewModel: ReportViewModel = hiltViewModel()

            // استدعاء MainScreen وتمرير الدوال اللازمة
            MainScreen(
                categoryViewModel = categoryViewModel,
                reportViewModel = reportViewModel,
                modifier = androidx.compose.ui.Modifier,
                // دالة تستدعي التنقل لشاشة إضافة التقرير
                onAddReportClick = {
                    navController.navigate("addReportScreen")
                }
            )
        }

        // شاشة إضافة التقرير
        composable("addReportScreen") {
            AddReportScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}