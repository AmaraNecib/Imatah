package com.example.imatah.data.repository

import com.example.imatah.data.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategories(): Flow<List<Category>>

}
