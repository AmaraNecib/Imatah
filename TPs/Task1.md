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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.imatah.ui.theme.ImatahTheme

class MainActivity : ComponentActivity() {
override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
enableEdgeToEdge() // تمكين الوضع Edge-to-Edge لواجهة المستخدم
setContent {
ImatahTheme {
// Scaffold يوفر هيكل تصميم أساسي من Material Design
Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
// تعريف متغيرات الحالة
var textValue by remember { mutableStateOf("") } // لحفظ قيمة النص المدخل
val allItems = remember { mutableStateListOf<String>() } // قائمة العناصر
var searchQuery by remember { mutableStateOf("") } // استعلام البحث

                    // استدعاء الدالة الرئيسية للواجهة مع تمرير الحالة
                    FirstUI(
                        modifier = Modifier.padding(innerPadding),
                        textValue = textValue,
                        onTextValueChange = { newValue -> textValue = newValue },
                        allItems = allItems,
                        searchQuery = searchQuery,
                        onAddItem = { newItem ->
                            if (newItem.isNotBlank()) {
                                allItems.add(newItem) // إضافة عنصر جديد إلى القائمة
                                textValue = "" // مسح حقل الإدخال
                            }
                        },
                        onSearch = { query ->
                            searchQuery = query // تعيين استعلام البحث
                        }
                    )
                }
            }
        }
    }
}

/**
* الدالة الرئيسية للواجهة
* @param modifier Modifier لتعديل التخطيط
* @param textValue قيمة النص المدخل
* @param onTextValueChange دالة تحديث قيمة النص
* @param allItems قائمة العناصر
* @param searchQuery استعلام البحث
* @param onAddItem دالة إضافة عنصر جديد
* @param onSearch دالة البحث
  */
  @Composable
  fun FirstUI(
  modifier: Modifier = Modifier,
  textValue: String,
  onTextValueChange: (String) -> Unit,
  allItems: List<String>,
  searchQuery: String,
  onAddItem: (String) -> Unit,
  onSearch: (String) -> Unit
  ) {
  // تصفية العناصر بناءً على استعلام البحث
  val displayedItems = if (searchQuery.isEmpty()) {
  allItems // عرض جميع العناصر إذا كان استعلام البحث فارغًا
  } else {
  allItems.filter { it.contains(searchQuery, ignoreCase = true) } // تصفية العناصر
  }

  Column(
  modifier = modifier
  .padding(25.dp)
  .fillMaxSize()
  ) {
  // شريط الإدخال والبحث
  SearchInputBar(
  textValue = textValue,
  onTextValueChange = onTextValueChange,
  onAddItem = onAddItem,
  onSearch = onSearch
  )

       // عرض العناصر في قائمة
       CardsList(displayedItems)
  }
  }

/**
* شريط الإدخال والبحث
* @param textValue قيمة النص المدخل
* @param onTextValueChange دالة تحديث قيمة النص
* @param onAddItem دالة إضافة عنصر جديد
* @param onSearch دالة البحث
  */
  @Composable
  fun SearchInputBar(
  textValue: String,
  onTextValueChange: (String) -> Unit,
  onAddItem: (String) -> Unit,
  onSearch: (String) -> Unit
  ) {
  Column {
  // حقل إدخال النص
  TextField(
  value = textValue,
  onValueChange = onTextValueChange,
  modifier = Modifier.fillMaxWidth(),
  placeholder = { Text("Enter text...") }
  )

       // صف يحتوي على زر الإضافة وزر البحث
       Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(vertical = 8.dp),
           horizontalArrangement = Arrangement.SpaceBetween
       ) {
           // زر الإضافة
           Button(onClick = { onAddItem(textValue) }) {
               Text("Add")
           }

           // زر البحث
           Button(onClick = { onSearch(textValue) }) {
               Text("Search")
           }
       }
  }
  }

/**
* عرض العناصر في قائمة باستخدام LazyColumn
* @param displayedItems قائمة العناصر المراد عرضها
  */
  @Composable
  fun CardsList(displayedItems: List<String>) {
  LazyColumn(modifier = Modifier.fillMaxSize()) {
  // إنشاء بطاقة لكل عنصر في القائمة
  items(displayedItems) { item ->
  Card(
  modifier = Modifier
  .fillMaxWidth()
  .padding(vertical = 4.dp),
  elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
  ) {
  Text(text = item, modifier = Modifier.padding(16.dp)) // عرض النص داخل البطاقة
  }
  }
  }
  }