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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.imatah.ui.theme.ImatahTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImatahTheme {
                // UPDATED:  TODO 0: Call the UI composable function
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FirstUI(modifier = Modifier.padding(innerPadding))
                    // Delete Greeting say hello Amara
                    //Greeting(name = "Amara", modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

/**
 * Main composable function for the UI layout
 * @param modifier Modifier for layout adjustments
 */
@Composable
fun FirstUI(modifier: Modifier = Modifier) {

    // UPDATED: TODO 1: Create state variables for text input and items list
    var textValue by remember { mutableStateOf("") }
    val allItems = remember { mutableStateListOf<String>() }
    var searchQuery by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // UPDATED: Filter Logic case senstive
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
            textValue = textValue,  // TODO 2: Connect to state
            onTextValueChange = { newText ->
                textValue = newText
                // UPDATED: /* TODO 3: Update text state */
                searchQuery = newText
                errorMessage = "" /* TODO 4: Add item to list */
            },
            onAddItem = {
                // UPDATED: التحقق من النص قبل الإضافة لمنع النص الفارغ
                if (textValue.isNotBlank()) {
                    allItems.add(textValue)
                    textValue = "" // مسح حقل الإدخال بعد الإضافة
                    errorMessage = ""
                } else {
                    errorMessage = "لا يمكنك إضافة نص فارغ!"
                }
            },
            onSearch = {
                // UPDATED: عند الضغط على زر البحث، يتم تعيين استعلام البحث للنص الحالي
                searchQuery = textValue
            }
        )

        // UPDATED: عرض رسالة الخطأ في حالة وجود خطأ في الإدخال
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }

        // UPDATED: عرض رسالة "No results found!" عند عدم وجود نتائج للبحث
        if (displayedItems.isEmpty() && searchQuery.isNotEmpty()) {
            Text(
                text = "No results found!",
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        } else {
            // UPDATE TODO 6: Display list of items using CardsList composable
            CardsList(displayedItems, onDelete = { item ->
                allItems.remove(item)
            })
        }
    }
}

/**
 * UPDATED: مكون إدخال النص والأزرار مع:
 * - TextField مرتبط بمتغير الحالة textValue
 * - زر "Add" يقوم بإضافة العنصر
 * - زر "Search" يقوم بتعيين قيمة البحث (رغم وجود البحث الفوري، نحتفظ به لدعم المطلوب)
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
            placeholder = { Text("Enter text...") }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // UPDATED: زر "Add" لاستدعاء onAddItem لإضافة العنصر
            Button(onClick = onAddItem) {
                Text("Add")
            }
            // UPDATED: زر "Search" لاستدعاء onSearch مع النص الحالي
            Button(onClick = { onSearch(textValue) }) {
                Text("Search")
            }
        }
    }
}

/**
 * UPDATED: CardsList تم تعديلها لعرض العناصر داخل LazyColumn
 * - تمت إضافة زر "Delete" لكل عنصر لحذف العنصر عند الضغط عليه
 */
@Composable
fun CardsList(displayedItems: List<String>, onDelete: (String) -> Unit) {
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
                    // UPDATED: عرض نص العنصر
                    Text(text = item)
                    // UPDATED: زر "Delete" لحذف العنصر
                    Button(
                        onClick = { onDelete(item) }
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FirstUIPreview() {
    ImatahTheme {
        FirstUI()
    }
}
