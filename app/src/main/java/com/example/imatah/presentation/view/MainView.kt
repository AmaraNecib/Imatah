package com.example.imatah.presentation.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.imatah.presentation.view.components.ImatahBottomNavigation
import com.example.imatah.presentation.view.components.ImatahTopBar
import com.example.imatah.presentation.view.components.ScreenContent
import com.example.imatah.presentation.viewmodel.CategoryViewModel
import com.example.imatah.presentation.viewmodel.ReportViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainView() {
    var currentRoute by remember { mutableStateOf("Home") }
    // إضافة متغير لتحديد ظهور شريط التنقل
    var showBottomBar by remember { mutableStateOf(true) }

    val categoryViewModel = hiltViewModel<CategoryViewModel>()
    val reportViewModel = hiltViewModel<ReportViewModel>()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ImatahTopBar() },
        // استخدام الشرط لعرض شريط التنقل أو لا
        bottomBar = {
            if (showBottomBar) {
                ImatahBottomNavigation(
                    currentRoute = currentRoute,
                    onRouteSelected = { route -> currentRoute = route }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x2F121212))
                .padding(top = 50.dp)
        ) {
            ScreenContent(
                currentRoute = currentRoute,
                categoryViewModel = categoryViewModel,
                reportViewModel = reportViewModel,
                onNavigate = { route, showBar ->
                    currentRoute = route
                    showBottomBar = showBar
                }
            )
        }
    }
}
