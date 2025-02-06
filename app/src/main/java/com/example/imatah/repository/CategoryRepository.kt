package com.example.imatah.repository

import com.example.imatah.model.Category

object CategoryRepository {
    fun getCategories(): List<Category> {
        return listOf(
            Category(1, "General", "General reports", "https://cdn-icons-png.flaticon.com/512/149/149071.png"),
            Category(2, "Urgent", "Urgent reports", "https://cdn-icons-png.flaticon.com/512/149/149119.png"),
            Category(3, "Maintenance", "Maintenance reports", "https://cdn-icons-png.flaticon.com/512/149/149120.png"),
            Category(4, "Infrastructure", "Road and building issues", "https://cdn-icons-png.flaticon.com/512/149/149125.png"),
            Category(5, "Utilities", "Water and electricity issues", "https://cdn-icons-png.flaticon.com/512/149/149130.png")
        )
    }
}
