package com.example.imatah.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imatah.model.Report
import com.example.imatah.repository.ReportRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UIState(
    val isLoading: Boolean = false,
    val reports: List<Report> = emptyList(),
    val error: String? = null
)

class ReportViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    init {
        loadReports()
    }

    fun loadReports() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                delay(2000)
                val reports = ReportRepository.getReports()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    reports = reports
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}
