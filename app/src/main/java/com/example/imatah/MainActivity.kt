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
        enableEdgeToEdge()
        setContent {
            ImatahTheme {
                // Scaffold provides basic material design layout structure
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // TODO 0: Call the UI composable function
                    // In FirstUI composable
                    var textValue by remember { mutableStateOf("") }//  تحفظ قيمة النص المدخل
                    val allItems = remember { mutableStateListOf<String>() }// قائمة العناصر
                    var searchQuery by remember { mutableStateOf("") }// دالة استعلام البحث
                    /****/
                    //FirstUI)
                    FirstUI(  // دالة مسؤولة عن عاد النص الحالي المدخل في حقل الكتابة
                        modifier = Modifier.padding(innerPadding),
                        textValue = textValue,
                        onTextValueChange = { newValue -> textValue = newValue} ) //  تحديث النص عند تغييره
                }
            }
        }
    }
}

/**
 * Main composable function for the UI layout
 * @param modifier Modifier for layout adjustments
 */
@Composable
fun FirstUI(
    modifier: Modifier = Modifier,
    textValue: String, // خزن النص الذي يكتبه المستخدم في TextField.
    onTextValueChange: (String) -> Unit, // عند تغيير النص داخل حقل الإدخال، وتقوم بتحديث textValue بالقيمة الجديدة.
    searchQuery: String, //يخزن النص الذي أدخله المستخدم في البحث
    onAddItem: (String) -> Unit, // إضافة العنصر الجديد إلى القائمة.
    onSearch: (String) -> Unit

) {
    // TODO 1: Create state variables for text input and items list
/**       انشاء متغيرات لادخال نص و قائمة عناصر : يعني متغير يمثل قيمة النص المدخل من طرف المستخدم ثم نسنده لحفظه ل  Unit
 */
    textValue: String, // النص الموجود في TextField
    onTextValueChang : (String)-> Unit//  اخذ النص الجديد المدخل و حفظه

    Column(
        modifier = modifier
            .padding(25.dp)
            .fillMaxSize()
    ) {
        //شريط الادخال و ازرار لاضافة عناصر ة البحث عنها
        SearchInputBar(
            textValue = textValue, // TODO 2: Connect to state  اسناد النص الموجود في حقل الإدخال
            onTextValueChange = onTextValueChange /* TODO 3: Update text state تحديث حالة النص بعد تغييره  */ ,
            onAddItem = onAddItem, /* TODO 4: Add item to list  اضافة عنصر جديد للقائمة  */
            onSearch =  onSearch /* TODO 5: Implement search functionality البث عن عنصر معين و تصفية القائمة */
        )

        // TODO 6: Display list of items using CardsList composable
        //  عرض قائمة العناصر باستخدام CardsList composable
        //CardsList(emptyList()) //  emptyList()  قائمة فارغة تمرر للدالة و بالتالي لن تظهر اي بطاقة على الواجهة
        CardsList(displayedItems) // لعرض العناصر نمرر قائمة بها عناصر
    }
}

/**
 * Composable for search and input controls
 * @param textValue Current value of the input field قيمة حقل الإدخال.
 * @param onTextValueChange Callback for text changes تحديث قيمة حقل الإدخال
 * @param onAddItem Callback for adding new items دالة إضافة عنصر جديد
 * @param onSearch Callback for performing search دالة البحث
 * @param allItems قائمة العناصر
 * @param FirstUI  الدالة الرئيسية المسؤولة عن بناء واجهة المستخدم
 */
@Composable
fun SearchInputBar(
    textValue: String,  // النص المدخل حاليًا
    onTextValueChange: (String) -> Unit,  // تحديث النص المدخل
    onAddItem: (String) -> Unit, //إضافة عنصر جديد
    onSearch: (String) -> Unit  // البحث في العناصر

) {
    Column {
        TextField(
            value = textValue,// هنا نعرض النص المدخل داخل الحقل
            onValueChange = onTextValueChange, // يتم استدعاء هذه الدالة عند الكتابة داخل الحقل حتى تتحدث القيمة
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter text...") }
        )

        Row(    //  زر الإضافة وزر البحث في صف
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { /* TODO 7: Handle add button click زر الإضافة */
                onAddItem(textValue) }) {
                Text("Add")
            }

            Button(onClick = { /* TODO 8: Handle search button click زر البحث */
                onSearch(textValue)}) {
                Text("Search")
            }
        }
    }
}

/**
 * Composable for displaying a list of items in cards
 * @param displayedItems List of items to display
 */
@Composable
fun CardsList(displayedItems: List<String>) {
    // TODO 9: Implement LazyColumn to display items
    //  تنفيذ LazyColumn لعرض العناصر
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        // TODO 10: Create cards for each item in the list  إنشاء بطاقة لكل عنصر في القائمة
        items(displayedItems) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(text = "Sample Item", modifier = Modifier.padding(16.dp)) // تعرض النص داخل البطاقة
            }
        }
    }
}

