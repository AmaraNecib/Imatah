package com.example.imatah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.imatah.presentation.view.MainView
import com.example.imatah.presentation.viewmodel.CategoryViewModel
import com.example.imatah.presentation.viewmodel.ReportViewModel
import com.example.imatah.ui.theme.ImatahTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // استخدام viewModels() لإنشاء نماذج العرض بواسطة Hilt
    private val categoryViewModel: CategoryViewModel by viewModels()
    private val reportViewModel: ReportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImatahTheme {
                // استخدام السطح للتطبيق بأكمله مع لون الخلفية من السمة
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // استخدام MainView بدلاً من AppNavigation
                    // هذا سيعرض شريط التنقل السفلي والشريط العلوي
                    MainView()
                }
            }
        }
    }
}
