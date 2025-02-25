package com.example.imatah.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imatah.data.model.Report
import com.example.imatah.data.repository.ReportRepository
import com.example.imatah.domain.usecase.GetReportsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UIState(
    val isLoading: Boolean = false,
    val reports: List<Report> = emptyList(),
    val error: String? = null
)


@HiltViewModel
class ReportViewModel @Inject constructor(private val getReportsUseCase: GetReportsUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    init {
        loadReports()
    }

    fun loadReports() {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(isLoading = true)
            delay(2000)

            getReportsUseCase().catch {
                _uiState.value = _uiState.value.copy(
                    reports = emptyList(),
                    isLoading = false,
                    error = "Failed to load reports"
                )
            }.collect{reports ->

                _uiState.value = _uiState.value.copy(
                    error = null,
                    isLoading = false,
                    reports = reports
                )

            }
        }
    }
}
