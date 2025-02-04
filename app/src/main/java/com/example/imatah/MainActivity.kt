package com.example.imatah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.imatah.ui.theme.ImatahTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImatahTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FirstUI(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun FirstUI(modifier: Modifier = Modifier) {
    // 1️⃣ متغيرات الحالة
    var textValue by remember { mutableStateOf("") }
    val allItems = remember { mutableStateListOf<String>() }
    var searchQuery by remember { mutableStateOf("") }

    // 2️⃣ تصفية القائمة بناءً على البحث
    val displayedItems = if (searchQuery.isEmpty()) {
        allItems
    } else {
        allItems.filter { it.contains(searchQuery, ignoreCase = true) }
    }

    Column(
        modifier = modifier
            .padding(25.dp)
            .fillMaxSize()
    ) {
        // 3️⃣ حقل الإدخال والأزرار
        SearchInputBar(
            textValue = textValue,
            onTextValueChange = { textValue = it },
            onAddItem = {
                if (textValue.isNotBlank()) {
                    allItems.add(textValue)
                    textValue = ""  // مسح الحقل بعد الإضافة
                }
            },
            onSearch = { searchQuery = it }
        )

        // 4️⃣ عرض القائمة
        CardsList(displayedItems)
    }
}

/**
 *  مكون لإدخال النص والبحث
 */
@Composable
fun SearchInputBar(
    textValue: String,
    onTextValueChange: (String) -> Unit,
    onAddItem: () -> Unit,
    onSearch: (String) -> Unit
) {
    Column {
        TextField(
            value = textValue,
            onValueChange = onTextValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("أدخل النص...") }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onAddItem) {
                Text("إضافة")
            }
            Button(onClick = { onSearch(textValue) }) {
                Text("بحث")
            }
        }
    }
}

/**
 *  مكون لعرض القائمة في بطاقات
 */
@Composable
fun CardsList(displayedItems: List<String>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(displayedItems) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(text = item, modifier = Modifier.padding(16.dp))
            }
        }
    }
}