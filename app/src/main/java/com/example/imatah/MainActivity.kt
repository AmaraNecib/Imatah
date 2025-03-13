package com.example.imatah

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.imatah.presentation.navigation.AppNavGraph
import com.example.imatah.presentation.viewmodel.CategoryViewModel
import com.example.imatah.presentation.viewmodel.ReportViewModel
import com.example.imatah.ui.theme.ImatahTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        requestLocationPermissions()

        setContent {
            val navController = rememberNavController()

            val categoryViewModel: CategoryViewModel = hiltViewModel()
            val reportViewModel: ReportViewModel = hiltViewModel()

            ImatahTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavGraph(
                        navController = navController,
                        categoryViewModel = categoryViewModel,
                        reportViewModel = reportViewModel
                    )
                }
            }
        }
    }

    private fun requestLocationPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
                val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

                if (fineLocationGranted || coarseLocationGranted) {

                } else {
                }
            }

        if (permissions.all {
                ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
            }) {

        } else {
            requestPermissionLauncher.launch(permissions)
        }
    }
}
