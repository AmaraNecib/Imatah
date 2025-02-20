package com.example.imatah.repository

import com.example.imatah.model.Category

object CategoryRepository {
    fun getCategories(): List<Category> {
        return listOf(
            Category(
                id = 1,
                name = "General",
                description = "General reports",
                imageUrl = "https://images.pexels.com/photos/30704109/pexels-photo-30704109/free-photo-of-outdoor-market-at-vienna-city-hall-in-autumn.jpeg?auto=compress&cs=tinysrgb&w=600&lazy=load",
            ),
            Category(
                id = 2,
                name = "Urgent",
                description = "Urgent reports",
                imageUrl = "https://images.pexels.com/photos/30394204/pexels-photo-30394204/free-photo-of-vibrant-historic-architecture-in-mexico-city.jpeg?auto=compress&cs=tinysrgb&w=600&lazy=load",
            ),
            Category(
                id = 3,
                name = "Maintenance",
                description = "Maintenance reports",
                imageUrl = "https://images.pexels.com/photos/16870065/pexels-photo-16870065/free-photo-of-a-pink-building-with-a-large-pink-banner-on-it.jpeg?auto=compress&cs=tinysrgb&w=600&lazy=load",
            )
        )
    }
}