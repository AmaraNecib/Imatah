package com.example.imatah.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.imatah.presentation.viewmodel.AddReportViewModel
import com.example.imatah.presentation.viewmodel.AddReportEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReportScreen(
    viewModel: AddReportViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Report", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Black)
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Title Input
                OutlinedTextField(
                    value = state.title,
                    onValueChange = { viewModel.onEvent(AddReportEvent.TitleChanged(it)) },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Description Input
                OutlinedTextField(
                    value = state.description,
                    onValueChange = { viewModel.onEvent(AddReportEvent.DescriptionChanged(it)) },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Image URL Input
                OutlinedTextField(
                    value = state.imageUrl,
                    onValueChange = { viewModel.onEvent(AddReportEvent.ImageUrlChanged(it)) },
                    label = { Text("Image URL") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                //Category
                CategoryDropdown(
                    selectedCategory = state.category,
                    onCategorySelected = { category ->
                        // عند اختيار فئة جديدة، نرسل الحدث للـ ViewModel
                        viewModel.onEvent(AddReportEvent.CategorySelected(category))
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Coordinates Input
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = state.coordinates.first.toString(),
                        onValueChange = { newLatString ->
                            val newLat = newLatString.toDoubleOrNull() ?: 0.0
                            viewModel.onEvent(AddReportEvent.CoordinatesChanged(newLat, state.coordinates.second))
                        },
                        label = { Text("Latitude") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = state.coordinates.second.toString(),
                        onValueChange = { newLongString ->
                            val newLong = newLongString.toDoubleOrNull() ?: 0.0
                            viewModel.onEvent(AddReportEvent.CoordinatesChanged(state.coordinates.first, newLong))
                        },
                        label = { Text("Longitude") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Status Dropdown
                var expandedStatus by remember { mutableStateOf(false) }
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = state.status,
                        onValueChange = { },
                        label = { Text("Status") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { expandedStatus = !expandedStatus }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = "Dropdown"
                                )
                            }
                        }
                    )
                    DropdownMenu(
                        expanded = expandedStatus,
                        onDismissRequest = { expandedStatus = false }
                    ) {
                        listOf("New", "In Progress", "Fixed").forEach { status ->
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.onEvent(AddReportEvent.StatusChanged(status))
                                    expandedStatus = false
                                },
                                text = { Text(status) }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Submit Button with Loading Indicator
                Button(
                    onClick = { viewModel.onEvent(AddReportEvent.Submit) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                    } else {
                        Text("Submit Report")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Error Message
                state.error?.let {
                    Text(text = it, color = Color.Red, modifier = Modifier.padding(8.dp))
                }

                // Success Message
                if (state.isSuccess) {
                    Text(text = "Report added successfully!", color = Color.Green, modifier = Modifier.padding(8.dp))
                }
            }
        }
    )
}
