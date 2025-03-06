package com.example.imatah.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.imatah.model.Category
import com.example.imatah.model.Report
import com.example.imatah.viewmodel.CategoryViewModel
import com.example.imatah.viewmodel.ReportViewModel

@Composable
fun MyScreen(
    categoryViewModel: CategoryViewModel = viewModel(),
    reportViewModel: ReportViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val categories by categoryViewModel.categoryState.collectAsState()
    val reportsState by reportViewModel.uiState.collectAsState()

    // تصفية التقارير بناءً على قيمة البحث (بالاسم أو الوصف أو الفئة)
    val filteredReports = reportsState.reports.filter { report ->
        report.name.contains(searchQuery, ignoreCase = true) ||
                report.description.contains(searchQuery, ignoreCase = true) ||
                report.category.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(top = 55.dp, bottom = 16.dp, start = 16.dp)
    ) {
        // شريط البحث مع ربط قيمة النص
        SearchBar(searchQuery = searchQuery, onQueryChange = { searchQuery = it })
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "بادر ولك الاجر إن شاء الله",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        ActionButtons()
        Spacer(modifier = Modifier.height(16.dp))

        // عرض التقارير المصفاة
        Column(modifier = Modifier.padding(start = 10.dp)) {
            SectionTitle("Nearly Road Need to Fix")
            LazyRow {
                items(filteredReports) { report ->
                    ReportItem(report = report) {
                        // هنا يمكنك استدعاء عملية الانتقال إلى شاشة التفاصيل
                        // مثال: navController.navigate("report_detail/${report.id}")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // عرض الفئات
        Column(modifier = Modifier.padding(start = 10.dp)) {
            SectionTitle("Popular Destination")
            LazyRow {
                items(categories.categories) { category ->
                    CategoryItem(category = category)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchQuery: String, onQueryChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, end = 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        TextField(
            value = searchQuery,
            onValueChange = onQueryChange,
            placeholder = { Text("Search nearly Volunteer ...", color = Color.Gray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.Black, shape = MaterialTheme.shapes.small)
                        .padding(6.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(50.dp)
                .background(Color.White, shape = MaterialTheme.shapes.extraLarge)
                .padding(horizontal = 12.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun ActionButtons() {
    Column(modifier = Modifier.padding(end = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ActionButton("Add damaged road", Icons.Default.Add)
            ActionButton("Progress Tracking", Icons.Default.Edit)
            ActionButton("Bookmark", Icons.Default.Favorite)
            ActionButton("Fixed", Icons.Default.Check)
        }
    }
}

@Composable
fun ActionButton(text: String, icon: ImageVector) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF)),
        modifier = Modifier
            .width(85.dp)
            .height(70.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = icon, contentDescription = text, tint = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = text, color = Color.Black, maxLines = 1, fontSize = 10.sp)
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = Color(0xFFFFD700),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun CategoryItem(category: Category) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(130.dp)
            .height(150.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box {
            AsyncImage(
                model = category.imageUrl,
                contentDescription = category.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20.dp))
            )
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.White, shape = MaterialTheme.shapes.medium)
                        .width(150.dp)
                ) {
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ReportItem(report: Report, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(290.dp)
            .height(170.dp)
            .padding(7.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(report.imageUrl),
                contentDescription = report.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.large)
            )
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White, shape = MaterialTheme.shapes.medium)
                        .width(280.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = report.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        modifier = Modifier.padding(end = 15.dp, top = 8.dp)
                    )
                    Text(
                        text = report.description,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        modifier = Modifier.padding(end = 15.dp, bottom = 8.dp)
                    )
                }
            }
        }
    }
}
