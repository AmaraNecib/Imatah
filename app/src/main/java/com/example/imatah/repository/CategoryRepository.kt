package com.example.imatah.repository

import com.example.imatah.model.Category

object CategoryRepository {
    fun getCategories(): List<Category> {
        return listOf(
            Category(
                id = 1,
                name = "General",
                description = "General reports",
                icon = "https://cdn-icons-png.flaticon.com/512/149/149071.png"
            ),
            Category(
                id = 2,
                name = "Urgent",
                description = "Urgent reports",
                icon = "https://cdn-icons-png.flaticon.com/512/149/149119.png"
            ),
            Category(
                id = 3,
                name = "Maintenance",
                description = "Maintenance reports",
                icon = "https://cdn-icons-png.flaticon.com/512/149/149120.png"
            )
        )
    }
}
