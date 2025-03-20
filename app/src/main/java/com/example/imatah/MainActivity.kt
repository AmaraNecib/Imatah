package com.example.imatah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.imatah.presentation.view.AppNavigation
import com.example.imatah.ui.theme.ImatahTheme
//import com.example.imatah.presentation.view.MainView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImatahTheme {
                val navController = rememberNavController() // ✅ إنشاء NavController
                AppNavigation(navController = navController) // ✅ تمريره إلى AppNavigation
            }
        }
    }
}
