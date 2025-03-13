package com.example.imatah.presentation.viewmodel

import java.util.UUID


data class AddReportStat(
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val imageUrl: String = "",
    val status: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val id: String = UUID.randomUUID().toString(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

