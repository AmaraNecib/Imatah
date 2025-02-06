package com.example.imatah.model

import java.util.Date

data class Report(
    val id: Long,
    val name: String,
    val description: String,
    val status: String,
    val category: String,
    val imageUrl: String,
    val coordinates: Pair<Double, Double>,
    val createdAt: Date,
    val updatedAt: Date
)
