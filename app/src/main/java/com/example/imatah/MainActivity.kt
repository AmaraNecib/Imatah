package com.example.imatah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.imatah.model.Category
import com.example.imatah.model.Report
import com.example.imatah.ui.theme.ImatahTheme
import com.example.imatah.view.MyScreen
import com.example.imatah.viewmodel.CategoryViewModel
import com.example.imatah.viewmodel.ReportViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImatahTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val categoryViewModel = viewModel<CategoryViewModel>()
                    val reportViewModel = viewModel<ReportViewModel>()
                    MyScreen(categoryViewModel, reportViewModel)
                }
            }
        }
    }
}

/**
 * Main composable function for the UI layout
 */
@Composable
fun FirstUI(modifier: Modifier = Modifier) {
    // ✅ إنشاء متغيرات الحالة
    var textValue by remember { mutableStateOf("") }
    val allItems = remember { mutableStateListOf<String>() }
    var searchQuery by remember { mutableStateOf("") }

    // ✅ تصفية القائمة حسب البحث
    val displayedItems = if (searchQuery.isEmpty()) {
        allItems
    } else {
        allItems.filter { it.contains(searchQuery, ignoreCase = true) }
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        // ✅ استدعاء شريط الإدخال وتمرير القيم
        SearchInputBar(
            textValue = textValue,
            onTextValueChange = { textValue = it },
            onAddItem = {
                if (it.isNotBlank()) {
                    allItems.add(it)
                    textValue = "" // إعادة تعيين الحقل بعد الإضافة
                }
            },
            onSearch = { searchQuery = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ عرض القائمة المحدثة
        CardsList(displayedItems, onDeleteItem = { allItems.remove(it) })
    }
}

/**
 * Composable for search and input controls
 */
@Composable
fun SearchInputBar(
    textValue: String,
    onTextValueChange: (String) -> Unit,
    onAddItem: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    Column {
        TextField(
            value = textValue,
            onValueChange = onTextValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter text...") }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { onAddItem(textValue) }) {
                Text("Add")
            }

            Button(onClick = { onSearch(textValue) }) {
                Text("Search")
            }
        }
    }
}

/**
 * Composable for displaying a list of items in cards
 */
@Composable
fun CardsList(displayedItems: List<String>, onDeleteItem: (String) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(displayedItems) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = item)
                    Button(onClick = { onDeleteItem(item) }) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUI() {
    ImatahTheme {
        FirstUI()
    }
}
