package com.example.imatah.presentation.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.imatah.presentation.view.AddReportScreen
import com.example.imatah.presentation.view.MainScreen
import com.example.imatah.presentation.viewmodel.CategoryViewModel
import com.example.imatah.presentation.viewmodel.ReportViewModel

@Composable
fun ScreenContent(
    currentRoute: String,
    categoryViewModel: CategoryViewModel,
    reportViewModel: ReportViewModel,
    modifier: Modifier = Modifier,
    onNavigate: (String, Boolean) -> Unit = { _, _ -> }
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (currentRoute) {
            "Home" -> MainScreen(
                categoryViewModel = categoryViewModel,
                reportViewModel = reportViewModel,
                modifier = modifier,
                onAddDamagedRoadClick = {
                    // التنقل إلى شاشة إضافة التقرير مع إخفاء شريط التنقل
                    onNavigate("AddReport", false)
                }
            )
            "Account" -> Text(text = "Account", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            "Map" -> Text(text = "Map", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            "Search" -> Text(text = "Search", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            "Quick-Report" -> Text(text = "Quick-Report", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            "AddReport" -> AddReportScreen(
                onNavigateBack = {
                    // العودة إلى الشاشة الرئيسية مع إظهار شريط التنقل
                    onNavigate("Home", true)
                }
            )
            else -> Text(text = "Unknown", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        }
    }
}
