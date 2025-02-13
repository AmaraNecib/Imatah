package com.example.imatah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    var textValue by remember { mutableStateOf("") }
    val allItems = remember { mutableStateListOf<String>() }
    var searchQuery by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) } // 🟢 Track if empty item is added

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
        SearchInputBar(
            textValue = textValue,
            onTextValueChange = { newValue ->
                textValue = newValue
                searchQuery = newValue // 🟢 Real-time search
                showError = false // Reset error on text change
            },
            onAddItem = {
                if (textValue.isNotBlank()) {
                    allItems.add(textValue)
                    textValue = ""
                    showError = false
                } else {
                    showError = true // 🟢 Show error if item is empty
                }
            },
            showError = showError // 🟢 Pass error state
        )

        if (displayedItems.isEmpty() && searchQuery.isNotEmpty()) {
            // 🟢 Show message if no search results
            Text(
                text = "No results found for '$searchQuery'",
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        CardsList(
            displayedItems = displayedItems,
            onDeleteItem = { item -> allItems.remove(item) } // 🟢 Pass delete functionality
        )
    }
}

@Composable
fun SearchInputBar(
    textValue: String,
    onTextValueChange: (String) -> Unit,
    onAddItem: (String) -> Unit,
    showError: Boolean // 🟢 Error state
) {
    Column {
        TextField(
            value = textValue,
            onValueChange = onTextValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter text...") },
            isError = showError // 🟢 Show error state in TextField
        )

        if (showError) {
            // 🟢 Show error message if item is empty
            Text(
                text = "Item cannot be empty!",
                color = Color.Red,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { onAddItem(textValue) }) {
                Text("Add")
            }
        }
    }
}

@Composable
fun CardsList(
    displayedItems: List<String>,
    onDeleteItem: (String) -> Unit // 🟢 Delete functionality
) {
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
                    Button(onClick = { onDeleteItem(item) }) { // 🟢 Delete button
                        Text("Delete")
                    }
                }
            }
        }
    }
}