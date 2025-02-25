package com.example.imatah.domain.usecase

import com.example.imatah.data.repository.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {

    operator fun invoke() = categoryRepository.getCategories()
}