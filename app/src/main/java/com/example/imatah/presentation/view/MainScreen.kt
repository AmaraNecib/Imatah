package com.example.imatah.presentation.view

import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.imatah.data.model.Category
import com.example.imatah.data.model.Report
import com.example.imatah.presentation.viewmodel.CategoryViewModel
import com.example.imatah.presentation.viewmodel.ReportViewModel

@Composable
fun MainScreen(
    categoryViewModel: CategoryViewModel,
    reportViewModel: ReportViewModel,
    modifier: Modifier = Modifier,
    onAddDamagedRoadClick: () -> Unit = {}
) {
    val categories by categoryViewModel.categoryState.collectAsState()
    val reports by reportViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(top = 55.dp, bottom = 16.dp, start = 16.dp)
    ) {

        SearchBar()

        Spacer(modifier = Modifier.height(20.dp))

        ActionButtons(onAddDamagedRoadClick = onAddDamagedRoadClick)

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.padding(start = 10.dp))  {
            SectionTitle("Nearly Road Need to Fix")
            LazyRow {
                items(reports.reports) { report ->
                    ReportItem(report = report)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
fun SearchBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, end = 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        TextField(
            value = "",
            onValueChange = {},
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
fun ActionButtons(onAddDamagedRoadClick: () -> Unit = {}) {
    Column (modifier = Modifier.padding(end = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ActionButton("Add", Icons.Default.Add, onClick = onAddDamagedRoadClick)
            ActionButton("Progress", Icons.Default.Edit)
            ActionButton("Bookmark", Icons.Default.Favorite)
            ActionButton("Fixed", Icons.Default.Check)
        }
    }
}

@Composable
fun ActionButton(text: String, Icon: ImageVector, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF)),
        modifier = Modifier
            .width(85.dp)
            .height(70.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icon, contentDescription = text, tint = Color.Black)
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
            .width(150.dp)
            .height(170.dp),
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
            ){
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
fun ReportItem(report: Report) {
    Card(
        modifier = Modifier
            .width(290.dp)
            .height(200.dp)
            .padding(7.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box {
            AsyncImage(
                model = report.imageUrl,
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
                Box(
                    modifier = Modifier
                        .background(Color.White, shape = MaterialTheme.shapes.medium)
                        .width(280.dp)
                ) {
                    Text(
                        text = report.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}
