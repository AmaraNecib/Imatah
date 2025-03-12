package com.example.imatah.presentation.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.imatah.presentation.viewmodel.AddReportViewModel
import com.example.imatah.ui.theme.DarkSurface
import com.example.imatah.ui.theme.RoadSignYellow
import com.example.imatah.ui.theme.SafetyGreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.util.regex.Pattern

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun AddReportScreen(
    viewModel: AddReportViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val locationState by viewModel.locationState.collectAsState()
    val statusOptions = listOf("New", "In Progress", "Fixed")
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // طلب أذونات الموقع
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    // إنشاء مطلق لاختيار الموقع من الخريطة
    val mapLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.toString()?.let { uri ->
                extractCoordinatesFromUri(uri)?.let { (lat, lng) ->
                    viewModel.onEvent(
                        AddReportViewModel.AddReportEvent.CoordinatesChanged(lat, lng)
                    )
                }
            }
        }
    }

    // انطلاق مختار الصور
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.onEvent(AddReportViewModel.AddReportEvent.ImageSelected(it)) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("إضافة تقرير مكان متضرر", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "رجوع",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkSurface
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // حقل العنوان
                OutlinedTextField(
                    value = state.title,
                    onValueChange = { viewModel.onEvent(AddReportViewModel.AddReportEvent.TitleChanged(it)) },
                    label = { Text("العنوان") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = RoadSignYellow,
                        focusedBorderColor = RoadSignYellow,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = RoadSignYellow,
                        unfocusedLabelColor = Color.Gray
                    )
                )

                // حقل الوصف
                OutlinedTextField(
                    value = state.description,
                    onValueChange = { viewModel.onEvent(AddReportViewModel.AddReportEvent.DescriptionChanged(it)) },
                    label = { Text("الوصف") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = RoadSignYellow,
                        focusedBorderColor = RoadSignYellow,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = RoadSignYellow,
                        unfocusedLabelColor = Color.Gray
                    ),
                    maxLines = 5
                )

                // قائمة الفئات المنسدلة
                var expandedCategory by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expandedCategory,
                    onExpandedChange = { expandedCategory = it }
                ) {
                    OutlinedTextField(
                        value = state.category,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("الفئة") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategory)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = RoadSignYellow,
                            focusedBorderColor = RoadSignYellow,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = RoadSignYellow,
                            unfocusedLabelColor = Color.Gray
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expandedCategory,
                        onDismissRequest = { expandedCategory = false },
                        modifier = Modifier.background(DarkSurface)
                    ) {
                        state.categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.name, color = Color.White) },
                                onClick = {
                                    viewModel.onEvent(AddReportViewModel.AddReportEvent.CategorySelected(category.name))
                                    expandedCategory = false
                                }
                            )
                        }
                    }
                }

                // قسم الموقع مع دعم تحديد الموقع التلقائي
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("الموقع", color = Color.White, style = MaterialTheme.typography.bodyLarge)

                    Spacer(modifier = Modifier.height(8.dp))

                    // عرض الإحداثيات الحالية
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = DarkSurface
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "خط العرض: ${state.coordinates.first}",
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "خط الطول: ${state.coordinates.second}",
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // أزرار تحديد الموقع (الجديدة والسابقة)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // زر اختيار الموقع من الخريطة (جديد)
                        Button(
                            onClick = {
                                try {
                                    val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="))
                                    mapLauncher.launch(mapIntent)
                                } catch (e: Exception) {
                                    // معالجة حالة عدم وجود تطبيق خرائط
                                }
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = RoadSignYellow)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "اختيار من الخريطة",
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("اختيار من الخريطة", color = Color.Black)
                        }

                        // زر تحديد الموقع التلقائي (السابق)
                        Button(
                            onClick = {
                                if (locationPermissionsState.allPermissionsGranted) {
                                    viewModel.getCurrentLocation(context)
                                } else {
                                    locationPermissionsState.launchMultiplePermissionRequest()
                                }
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = RoadSignYellow)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "موقعي الحالي",
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("موقعي الحالي", color = Color.Black)
                        }
                    }

                    // عرض حالة تحديد الموقع
                    when (locationState) {
                        is AddReportViewModel.LocationState.Loading -> {
                            LinearProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                color = RoadSignYellow
                            )
                        }
                        is AddReportViewModel.LocationState.Error -> {
                            val errorState = locationState as AddReportViewModel.LocationState.Error
                            Text(
                                text = errorState.message,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                        is AddReportViewModel.LocationState.Success -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "نجاح",
                                    tint = SafetyGreen
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "تم تحديد موقعك بنجاح!",
                                    color = SafetyGreen
                                )
                            }
                        }
                        else -> { /* لا شيء */ }
                    }
                }

                // قسم اختيار الصور
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("صورة الطريق المتضرر", color = Color.White, style = MaterialTheme.typography.bodyLarge)

                    Spacer(modifier = Modifier.height(8.dp))

                    // عرض الصورة المختارة
                    state.imageUri?.let { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = "الصورة المختارة",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(MaterialTheme.shapes.medium),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // زر اختيار الصور من المعرض
                    Button(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = RoadSignYellow)
                    ) {
                        Text("اختيار من المعرض", color = Color.Black)
                    }
                }

                // أزرار اختيار الحالة
                Text("الحالة", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                Column {
                    statusOptions.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = (option == state.status),
                                    onClick = {
                                        viewModel.onEvent(AddReportViewModel.AddReportEvent.StatusChanged(option))
                                    }
                                )
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (option == state.status),
                                onClick = {
                                    viewModel.onEvent(AddReportViewModel.AddReportEvent.StatusChanged(option))
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = RoadSignYellow,
                                    unselectedColor = Color.Gray
                                )
                            )
                            Text(
                                text = option,
                                color = Color.White,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }

                // زر الإرسال
                Button(
                    onClick = { viewModel.onEvent(AddReportViewModel.AddReportEvent.Submit) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = RoadSignYellow)
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text("إرسال التقرير", color = Color.Black)
                    }
                }

                // رسالة الخطأ
                if (state.error != null) {
                    Text(
                        text = state.error!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // رسالة النجاح
                if (state.isSuccess) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = SafetyGreen.copy(alpha = 0.1f),
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "نجاح",
                            tint = SafetyGreen
                        )
                        Text(
                            text = "تم إرسال التقرير بنجاح!",
                            color = SafetyGreen
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// دالة مساعدة لاستخراج الإحداثيات من URI
private fun extractCoordinatesFromUri(uri: String): Pair<Double, Double>? {
    val pattern = Pattern.compile("geo:(-?\\d+\\.\\d+),(-?\\d+\\.\\d+)")
    val matcher = pattern.matcher(uri)
    return if (matcher.find()) {
        Pair(matcher.group(1)!!.toDouble(), matcher.group(2)!!.toDouble())
    } else {
        null
    }
}
