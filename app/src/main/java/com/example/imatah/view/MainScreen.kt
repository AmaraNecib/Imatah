            @file:OptIn(ExperimentalMaterial3Api::class)
            
            package com.example.imatah.view
            
            import androidx.compose.foundation.background
            import androidx.compose.foundation.clickable
            import androidx.compose.foundation.layout.*
            import androidx.compose.foundation.lazy.LazyColumn
            import androidx.compose.foundation.lazy.LazyRow
            import androidx.compose.foundation.lazy.items
            import androidx.compose.material.icons.Icons
            import androidx.compose.material.icons.filled.ArrowBack
            import androidx.compose.material.icons.filled.Person
            import androidx.compose.material.icons.filled.Search
            import androidx.compose.material3.*
            import androidx.compose.runtime.*
            import androidx.compose.ui.Alignment
            import androidx.compose.ui.Modifier
            import androidx.compose.ui.graphics.Color
            import androidx.compose.ui.text.TextStyle
            import androidx.compose.ui.text.font.FontWeight
            import androidx.compose.ui.unit.dp
            import androidx.lifecycle.viewmodel.compose.viewModel
            import coil.compose.AsyncImage
            import com.example.imatah.model.Category
            import com.example.imatah.model.Report
            import com.example.imatah.viewmodel.CategoryViewModel
            import com.example.imatah.viewmodel.ReportViewModel
            
            @Composable
            fun MainScreen(
                categoryViewModel: CategoryViewModel = viewModel(),
                reportViewModel: ReportViewModel = viewModel(),
                onReportClick: (Report) -> Unit = {}
            ) {
                val categoriesState by categoryViewModel.categoryState.collectAsState()
                val reportState by reportViewModel.uiState.collectAsState()
            
                var searchQuery by remember { mutableStateOf("") }
                var selectedCategory by remember { mutableStateOf<String?>(null) }
                var selectedReport by remember { mutableStateOf<Report?>(null) }
            
                val filteredReports = reportState.reports.filter { report ->
                    val matchesSearch = searchQuery.isBlank() ||
                            report.name.contains(searchQuery, ignoreCase = true) ||
                            report.category.contains(searchQuery, ignoreCase = true)
                    val matchesCategory = selectedCategory == null || report.category == selectedCategory
                    matchesSearch && matchesCategory
                }
            
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    TopSection(
                        searchQuery = searchQuery,
                        onSearchQueryChange = { searchQuery = it }
                    )
            
                    if (reportState.isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.White)
                        }
                    } else {
                        if (selectedReport != null) {
                            ReportDetail(report = selectedReport!!) {
                                selectedReport = null
                            }
                        } else {
                            Text(
                                text = "Categories",
                                style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyRow(
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                items(categoriesState.categories) { category ->
                                    CategoryItem(
                                        category = category,
                                        isSelected = (selectedCategory == category.name),
                                        onCategorySelected = {
                                            selectedCategory = if (selectedCategory == category.name) {
                                                null
                                            } else {
                                                category.name
                                            }
                                        }
                                    )
                                }
                            }
                            Text(
                                text = "Reports",
                                style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyColumn(
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                items(filteredReports) { report ->
                                    ReportItem(report = report) {
                                        selectedReport = it
                                        onReportClick(it)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            @Composable
            fun TopSection(
                searchQuery: String,
                onSearchQueryChange: (String) -> Unit
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "IMATAH",
                                style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
                            )
                            Text(
                                text = "Chaymaa Heded",
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                            )
                        }
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
            
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChange,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                tint = Color.Gray
                            )
                        },
                        placeholder = {
                            Text("Search nearby Volunteers...", color = Color.Gray)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(color = Color.White),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.DarkGray,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.Gray,
                            cursorColor = Color.White,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
            
                    // Centered text with a bolder style
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "بادر ولك الاجر ان شاء الله",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
            
            @Composable
            fun CategoryItem(
                category: Category,
                isSelected: Boolean,
                onCategorySelected: () -> Unit
            ) {
                val containerColor = if (isSelected) Color.Gray else Color.DarkGray
            
                Card(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .width(110.dp)
                        .clickable { onCategorySelected() },
                    colors = CardDefaults.cardColors(containerColor = containerColor)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Reduced icon size
                        AsyncImage(
                            model = category.icon,
                            contentDescription = category.name,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = category.name,
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
            
            @Composable
            fun ReportItem(
                report: Report,
                onReportSelected: (Report) -> Unit
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { onReportSelected(report) },
                    colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        AsyncImage(
                            model = report.imageUrl,
                            contentDescription = report.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                        )
                        Text(
                            text = report.name,
                            style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = report.description,
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            text = "Status: ${report.status}",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
            
            @Composable
            fun ReportDetail(
                report: Report,
                onBack: () -> Unit
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    AsyncImage(
                        model = report.imageUrl,
                        contentDescription = report.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .padding(16.dp)
                    )
                    Text(
                        text = report.name,
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    Text(
                        text = report.description,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                    Text(
                        text = "Status: ${report.status}",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }