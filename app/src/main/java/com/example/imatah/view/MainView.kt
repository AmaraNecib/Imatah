package com.example.imatah.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imatah.view.components.ImatahBottomNavigation
import com.example.imatah.view.components.ImatahTopBar
import com.example.imatah.view.components.ScreenContent
import com.example.imatah.viewmodel.CategoryViewModel
import com.example.imatah.viewmodel.ReportViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainView() {
    var currentRoute by remember { mutableStateOf("Home") }
    val categoryViewModel = viewModel<CategoryViewModel>()
    val reportViewModel = viewModel<ReportViewModel>()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ImatahTopBar() },
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
               .padding(top=50.dp)
        ) {
            ScreenContent(
                currentRoute = currentRoute,
                categoryViewModel = categoryViewModel,
                reportViewModel = reportViewModel,
//                modifier = Modifier.fillMaxSize().padding(innerPadding)
            )
        }
    }
}
