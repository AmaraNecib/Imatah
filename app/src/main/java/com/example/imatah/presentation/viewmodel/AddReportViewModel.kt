package com.example.imatah.presentation.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imatah.data.model.Category
import com.example.imatah.data.model.Report
import com.example.imatah.data.repository.CategoryRepository
import com.example.imatah.domain.usecase.AddReportUseCase
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddReportViewModel @Inject constructor(
    private val addReportUseCase: AddReportUseCase,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    // حالة النموذج مع دعم Uri للصورة بدلاً من رابط URL
    data class AddReportState(
        val title: String = "",
        val description: String = "",
        val category: String = "",
        val coordinates: Pair<Double, Double> = Pair(0.0, 0.0),
        val imageUri: Uri? = null,
        val imageUrl: String = "", // احتفظ بهذا للتوافق مع الواجهة الخلفية
        val status: String = "New",
        val isLoading: Boolean = false,
        val error: String? = null,
        val isSuccess: Boolean = false,
        val categories: List<Category> = emptyList()
    )

    // أحداث النموذج مع دعم اختيار الصور
    sealed class AddReportEvent {
        data class TitleChanged(val title: String) : AddReportEvent()
        data class DescriptionChanged(val description: String) : AddReportEvent()
        data class CategorySelected(val category: String) : AddReportEvent()
        data class CoordinatesChanged(val latitude: Double, val longitude: Double) : AddReportEvent()
        data class ImageSelected(val uri: Uri) : AddReportEvent()
        data class StatusChanged(val status: String) : AddReportEvent()
        object Submit : AddReportEvent()
    }

    // حالات تحديد الموقع
    sealed class LocationState {
        object Initial : LocationState()
        object Loading : LocationState()
        object Success : LocationState()
        data class Error(val message: String) : LocationState()
    }

    private val _state = MutableStateFlow(AddReportState())
    val state: StateFlow<AddReportState> = _state.asStateFlow()

    private val _locationState = MutableStateFlow<LocationState>(LocationState.Initial)
    val locationState: StateFlow<LocationState> = _locationState

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getCategories().collect { categories ->
                _state.value = _state.value.copy(categories = categories)
            }
        }
    }

    fun onEvent(event: AddReportEvent) {
        when (event) {
            is AddReportEvent.TitleChanged -> {
                _state.value = _state.value.copy(title = event.title)
            }
            is AddReportEvent.DescriptionChanged -> {
                _state.value = _state.value.copy(description = event.description)
            }
            is AddReportEvent.CategorySelected -> {
                _state.value = _state.value.copy(category = event.category)
            }
            is AddReportEvent.CoordinatesChanged -> {
                _state.value = _state.value.copy(
                    coordinates = Pair(event.latitude, event.longitude)
                )
            }
            is AddReportEvent.ImageSelected -> {
                _state.value = _state.value.copy(
                    imageUri = event.uri,
                    imageUrl = event.uri.toString() // استخدم URI كقيمة مؤقتة للـ URL
                )
            }
            is AddReportEvent.StatusChanged -> {
                _state.value = _state.value.copy(status = event.status)
            }
            is AddReportEvent.Submit -> {
                submitReport()
            }
        }
    }

    // دالة للحصول على الموقع الحالي
    fun getCurrentLocation(context: Context) {
        _locationState.value = LocationState.Loading

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        try {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        _state.value = _state.value.copy(
                            coordinates = Pair(location.latitude, location.longitude)
                        )
                        _locationState.value = LocationState.Success
                    } else {
                        _locationState.value = LocationState.Error("لم يتم العثور على الموقع")
                    }
                }.addOnFailureListener { e ->
                    _locationState.value = LocationState.Error(e.message ?: "خطأ غير معروف")
                }
            } else {
                _locationState.value = LocationState.Error("يرجى منح إذن الوصول للموقع")
            }
        } catch (e: Exception) {
            _locationState.value = LocationState.Error(e.message ?: "خطأ غير معروف")
        }
    }

    private fun submitReport() {
        val currentState = _state.value

        // التحقق من صحة المدخلات
        if (currentState.title.isBlank()) {
            _state.value = currentState.copy(error = "العنوان لا يمكن أن يكون فارغاً")
            return
        }

        if (currentState.description.isBlank()) {
            _state.value = currentState.copy(error = "الوصف لا يمكن أن يكون فارغاً")
            return
        }

        if (currentState.category.isBlank()) {
            _state.value = currentState.copy(error = "يرجى اختيار فئة")
            return
        }

        if (currentState.imageUri == null) {
            _state.value = currentState.copy(error = "يرجى إضافة صورة")
            return
        }

        // إنشاء تقرير جديد
        val now = Date()
        val report = Report(
            id = 0, // سيتم تعيينه من قبل المستودع
            name = currentState.title,
            description = currentState.description,
            status = currentState.status,
            category = currentState.category,
            imageUrl = currentState.imageUrl,
            coordinates = currentState.coordinates,
            createdAt = now,
            updatedAt = now
        )

        // إرسال التقرير
        viewModelScope.launch {
            _state.value = currentState.copy(isLoading = true, error = null)

            try {
                addReportUseCase.invoke(report)
                _state.value = currentState.copy(isLoading = false, isSuccess = true)
            } catch (e: Exception) {
                _state.value = currentState.copy(
                    isLoading = false,
                    error = e.message ?: "حدث خطأ غير معروف"
                )
            }
        }
    }
}
