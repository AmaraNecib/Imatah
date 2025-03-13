package com.example.imatah.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imatah.data.model.Report
import com.example.imatah.domain.usecase.AddReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddReportViewModel @Inject constructor(
    private val addReportUseCase: AddReportUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddReportState())
    val state: StateFlow<AddReportState> = _state

    fun onEvent(event: AddReportEvent) {
        when (event) {
            is AddReportEvent.TitleChanged -> _state.update { it.copy(title = event.title) }
            is AddReportEvent.DescriptionChanged -> _state.update { it.copy(description = event.description) }
            is AddReportEvent.CategorySelected -> _state.update { it.copy(category = event.category) }
            is AddReportEvent.ImageUrlChanged -> _state.update { it.copy(imageUrl = event.url) }
            is AddReportEvent.StatusChanged -> _state.update { it.copy(status = event.status) }

            is AddReportEvent.CoordinatesChanged -> {
                println("📍 Received CoordinatesChanged Event: (${event.latitude}, ${event.longitude})")

                val newCoordinates = Pair(event.latitude, event.longitude)
                _state.update {
                    println("✅ Updating state with new coordinates: $newCoordinates")
                    it.copy(coordinates = newCoordinates, error = null)
                }
            }

            is AddReportEvent.Submit -> submitReport()
        }
    }


    private fun submitReport() {
        val current = _state.value


        // اطبع القيم الحالية للتحقق
        println("Title = ${current.title}")
        println("Description = ${current.description}")
        println("Category = ${current.category}")
        println("ImageUrl = ${current.imageUrl}")
        println("Coordinates = ${current.coordinates}")

        // تحقق من صحة المدخلات
        if (current.title.isBlank() ||
            current.description.isBlank() ||
            current.category.isBlank() ||
            current.imageUrl.isBlank() ||
            (current.coordinates.first == 0.0 && current.coordinates.second == 0.0)
        ) {
            _state.update { it.copy(error = "Please fill all required fields") }
            return
        }
        // ✅ التحقق من صحة الإحداثيات
        if (!isValidCoordinates(current.coordinates)) {
            _state.update { it.copy(error = "Invalid coordinates. Please enter a valid location.") }
            return
        }

        // ✅ تحقق من صحة المدخلات الأخرى
        if (current.title.isBlank() ||
            current.description.isBlank() ||
            current.category.isBlank() ||
            current.imageUrl.isBlank()
        ) {
            _state.update { it.copy(error = "Please fill all required fields") }
            return
        }

        // ✅ بدء التحميل
        _state.update { it.copy(isLoading = true, error = null, isSuccess = false) }

        viewModelScope.launch {
            try {
                val report = Report(
                    id = 1L,
                    name = current.title,
                    description = current.description,
                    status = current.status,
                    category = current.category,
                    imageUrl = current.imageUrl,
                    coordinates = current.coordinates,
                    createdAt = Date(),
                    updatedAt = Date()
                )

                addReportUseCase(report) // إرسال التقرير

                // ✅ نجاح العملية
                _state.update { AddReportState(isSuccess = true) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "An unexpected error occurred") }
            }
        }
    }


    // 🔍 دالة للتحقق من صحة الإحداثيات
    private fun isValidCoordinates(coordinates: Pair<Double, Double>): Boolean {
        val (latitude, longitude) = coordinates

        // ✅ تأكد أن القيم ليست (0.0, 0.0) ما لم يتم إدخالها يدويًا
        if (latitude == 0.0 && longitude == 0.0) return false

        // ✅ تأكد أن القيم ضمن النطاق المسموح به
        return latitude in -90.0..90.0 && longitude in -180.0..180.0
    }



}

data class AddReportState(
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val coordinates: Pair<Double, Double> = Pair(0.0, 0.0),
    val imageUrl: String = "",
    val status: String = "New",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

sealed class AddReportEvent {
    data class TitleChanged(val title: String) : AddReportEvent()
    data class DescriptionChanged(val description: String) : AddReportEvent()
    data class CategorySelected(val category: String) : AddReportEvent()
    data class CoordinatesChanged(val latitude: Double, val longitude: Double) : AddReportEvent()
    data class ImageUrlChanged(val url: String) : AddReportEvent()
    data class StatusChanged(val status: String) : AddReportEvent()
    object Submit : AddReportEvent()
}
