package com.example.imatah.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imatah.domain.usecase.AddReportUseCase
import com.example.imatah.data.model.Report
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class AddReportState(
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val imageUrl: String = "",
    val status: String = "New",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val successMessage: String? = null
)


sealed class AddReportEvent {
    data class TitleChanged(val title: String) : AddReportEvent()
    data class DescriptionChanged(val description: String) : AddReportEvent()
    data class CategorySelected(val category: String) : AddReportEvent()
    data class CoordinatesChanged(val latitude: Double, val longitude: Double) : AddReportEvent()
    data class ImageUrlChanged(val imageUrl: String) : AddReportEvent()
    data class StatusChanged(val status: String) : AddReportEvent()
    object FetchLocation : AddReportEvent() // ✅ جلب الموقع تلقائيًا
    object Submit : AddReportEvent()
}

@HiltViewModel
class AddReportViewModel @Inject constructor(
    private val addReportUseCase: AddReportUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddReportState())
    val state: StateFlow<AddReportState> = _state

    private var fusedLocationClient: FusedLocationProviderClient? = null


    fun onEvent(event: AddReportEvent, context: Context? = null) {
        when (event) {
            is AddReportEvent.TitleChanged -> _state.value = _state.value.copy(title = event.title)
            is AddReportEvent.DescriptionChanged -> _state.value = _state.value.copy(description = event.description)
            is AddReportEvent.CategorySelected -> _state.value = _state.value.copy(category = event.category)
            is AddReportEvent.CoordinatesChanged -> _state.value = _state.value.copy(latitude = event.latitude, longitude = event.longitude)
            is AddReportEvent.ImageUrlChanged -> _state.value = _state.value.copy(imageUrl = event.imageUrl)
            is AddReportEvent.StatusChanged -> _state.value = _state.value.copy(status = event.status)
            is AddReportEvent.FetchLocation -> context?.let { fetchCurrentLocation(it) } // ✅ جلب الموقع الحقيقي
            is AddReportEvent.Submit -> submitReport()
        }
    }
    @SuppressLint("MissingPermission")
    private fun fetchCurrentLocation(context: Context) {
        if (fusedLocationClient == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        }

        fusedLocationClient?.lastLocation?.addOnSuccessListener { location: Location? ->
            if (location != null) {
                _state.value = _state.value.copy(latitude = location.latitude, longitude = location.longitude)
            } else {
                _state.value = _state.value.copy(error = "تعذر الحصول على الموقع الحالي! تأكد من تفعيل GPS.")
            }
        }?.addOnFailureListener {
            _state.value = _state.value.copy(error = "حدث خطأ أثناء جلب الموقع: ${it.message}")
        }
    }


    private fun submitReport() {
        if (_state.value.title.isBlank() || _state.value.description.isBlank() || _state.value.category.isBlank()) {
            _state.value = _state.value.copy(error = "جميع الحقول مطلوبة!")
            return
        }

        if (_state.value.latitude == 0.0 && _state.value.longitude == 0.0) {
            _state.value = _state.value.copy(error = "يرجى تحديد موقع الإبلاغ! 📍")
            return
        }

        _state.value = _state.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val newReport = Report(
                    id = System.currentTimeMillis(),
                    name = _state.value.title,
                    description = _state.value.description,
                    category = _state.value.category,
                    imageUrl = _state.value.imageUrl,
                    status = _state.value.status,
                    coordinates = Pair(_state.value.latitude, _state.value.longitude),
                    createdAt = Date(),
                    updatedAt = Date()
                )
                addReportUseCase(newReport)
                _state.value = _state.value.copy(
                    isLoading = false,
                    isSuccess = true,
                    successMessage = "تمت إضافة التقرير بنجاح! ✅"
                )


                resetForm()

            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    private fun resetForm() {
        _state.value = _state.value.copy(
            title = "",
            description = "",
            category = "",
            latitude = 0.0,
            longitude = 0.0,
            imageUrl = "",
            status = "New",
            isSuccess = false
        )
    }
}
