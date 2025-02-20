package com.example.imatah.repository

import com.example.imatah.model.Category

object CategoryRepository {
    fun getCategories(): List<Category> {
        return listOf(
            Category(
                id = 1,
                name = "General",
                description = "General reports",
                imageUrl = "https://picsum.photos/id/240/600/400",
            ),
            Category(
                id = 2,
                name = "Urgent",
                description = "Urgent reports",
                imageUrl = "https://picsum.photos/id/240/600/400",
            ),
            Category(
                id = 3,
                name = "Maintenance",
                description = "Maintenance reports",
                imageUrl = "https://picsum.photos/id/240/600/400",
            )
        )
    }
}