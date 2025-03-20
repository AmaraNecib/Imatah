package com.example.imatah.presentation.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.imatah.presentation.view.components.ImatahBottomNavigation
import com.example.imatah.presentation.view.components.ImatahTopBar
import com.example.imatah.presentation.view.components.MainScreen
import com.example.imatah.presentation.viewmodel.CategoryViewModel
import com.example.imatah.presentation.viewmodel.ReportViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainView() {
    var currentRoute by remember { mutableStateOf("Home") }
    val categoryViewModel = hiltViewModel<CategoryViewModel>()
    val reportViewModel = hiltViewModel<ReportViewModel>()
    // تعريف navController لاستخدامه في التنقل
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ImatahTopBar(navController) },
        bottomBar = {
            ImatahBottomNavigation(
                currentRoute = currentRoute,
                onRouteSelected = { route -> currentRoute = route }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(top = 50.dp)
        ) {
            MainScreen(
                navController = navController, // ✅ تمرير navController
                categoryViewModel = categoryViewModel,
                reportViewModel = reportViewModel,
                modifier = Modifier.fillMaxSize(),
                onNavigateToAddReport = { navController.navigate("addReportScreen") } // ✅ استخدام الاسم الصحيح
            )
        }
    }
}
