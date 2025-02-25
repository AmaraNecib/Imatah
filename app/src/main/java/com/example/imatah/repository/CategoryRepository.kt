package com.example.imatah.repository
//TP
import com.example.imatah.model.Category

object CategoryRepository {
    fun getCategories(): List<Category> {
        return listOf(
            Category(
                id = 1,
                name = "General",
                description = "General reports",
                imageUrl = "https://images.pexels.com/photos/24828635/pexels-photo-24828635/free-photo-of-close-up-of-a-bronze-statue.jpeg?auto=compress&cs=tinysrgb&w=600",
            ),
            Category(
                id = 2,
                name = "Urgent",
                description = "Urgent reports",
                imageUrl = "https://images.pexels.com/photos/30788050/pexels-photo-30788050/free-photo-of-firefighter-tackles-intense-car-blaze-outdoors.jpeg?auto=compress&cs=tinysrgb&w=600",
            ),
            Category(
                id = 3,
                name = "Maintenance",
                description = "Maintenance reports",
                imageUrl = "https://images.pexels.com/photos/175039/pexels-photo-175039.jpeg?auto=compress&cs=tinysrgb&w=600",
            )
        )
    }
}