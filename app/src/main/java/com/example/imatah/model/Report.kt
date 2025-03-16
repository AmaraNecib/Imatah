
package com.example.imatah.model

import androidx.annotation.DrawableRes
import java.util.Date

data class Report(
    val id: Long,
    val name: String,
    val description: String,
    val status: String,
    val category: String,
    @DrawableRes val imageUrl: Int,
    val coordinates: Pair<Double, Double>,
    val createdAt: Date,
    val updatedAt: Date
)