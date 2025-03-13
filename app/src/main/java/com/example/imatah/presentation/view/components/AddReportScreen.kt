package com.example.imatah.presentation.view.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.imatah.presentation.viewmodel.AddReportViewModel
import com.example.imatah.presentation.viewmodel.AddReportEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReportScreen(navController: NavController, viewModel: AddReportViewModel, context: Context) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(AddReportEvent.FetchLocation, context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add New Report", color = Color.White, style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.title,
            onValueChange = { viewModel.onEvent(AddReportEvent.TitleChanged(it)) },
            label = { Text("Title", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.description,
            onValueChange = { viewModel.onEvent(AddReportEvent.DescriptionChanged(it)) },
            label = { Text("Description", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(8.dp))

        var expandedCategory by remember { mutableStateOf(false) }
        var selectedCategory by remember { mutableStateOf("Select Category") }

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                trailingIcon = {
                    IconButton(onClick = { expandedCategory = true }) {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown", tint = Color.White)
                    }
                }
            )
            DropdownMenu(expanded = expandedCategory, onDismissRequest = { expandedCategory = false }) {
                listOf("Road Issue", "Pollution", "Accident").forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedCategory = category
                            viewModel.onEvent(AddReportEvent.CategorySelected(category))
                            expandedCategory = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = "Latitude: ${state.latitude}, Longitude: ${state.longitude}",
            onValueChange = {},
            readOnly = true,
            label = { Text("Location", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(color = Color.White)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.imageUrl,
            onValueChange = { viewModel.onEvent(AddReportEvent.ImageUrlChanged(it)) },
            label = { Text("Image URL", color = Color.White) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(8.dp))

        var expandedStatus by remember { mutableStateOf(false) }
        var selectedStatus by remember { mutableStateOf("New") }

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedStatus,
                onValueChange = {},
                readOnly = true,
                label = { Text("Status", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                trailingIcon = {
                    IconButton(onClick = { expandedStatus = true }) {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown", tint = Color.White)
                    }
                }
            )
            DropdownMenu(expanded = expandedStatus, onDismissRequest = { expandedStatus = false }) {
                listOf("New", "In Progress", "Fixed").forEach { status ->
                    DropdownMenuItem(
                        text = { Text(status) },
                        onClick = {
                            selectedStatus = status
                            viewModel.onEvent(AddReportEvent.StatusChanged(status))
                            expandedStatus = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.onEvent(AddReportEvent.Submit) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
        ) {
            Text(text = "Submit Report", color = Color.Black)
        }

        if (state.isLoading) {
            CircularProgressIndicator(color = Color.White, modifier = Modifier.padding(16.dp))
        }

        state.error?.let { errorMessage ->
            Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(8.dp))
        }

        if (state.isSuccess) {
            Text(text = "Report added successfully!", color = Color.Green, modifier = Modifier.padding(8.dp))
        }
    }
}
