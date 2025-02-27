package com.example.imatah.repository

import com.example.imatah.model.Category
import com.example.imatah.R

object CategoryRepository {
    fun getCategories(): List<Category> {
        return listOf(
            Category(
                id = 1,
                name = "El Oued",
                description = "General reports",
                imageUrl = R.drawable.t3.toString().toString(),
            ),
            Category(
                id = 2,
                name = "Guemar",
                description = "Urgent reports",
                imageUrl = R.drawable.t2.toString().toString(),
            ),
            Category(
                id = 3,
                name = "Z'Goum",
                description = "Maintenance reports",
                imageUrl = R.drawable.t1.toString().toString(),

            )
        ) }
}
