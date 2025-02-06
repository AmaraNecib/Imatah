package com.example.imatah.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imatah.model.Category
import com.example.imatah.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CategoryState(
    val categories: List<Category> = emptyList()
)

class CategoryViewModel : ViewModel() {

    private val _categoryState = MutableStateFlow(CategoryState())
    val categoryState: StateFlow<CategoryState> = _categoryState.asStateFlow()

    init {
        // Fetch categories when ViewModel is initialized
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            val categories = CategoryRepository.getCategories()
            _categoryState.value = _categoryState.value.copy(categories = categories)
        }
    }
}
