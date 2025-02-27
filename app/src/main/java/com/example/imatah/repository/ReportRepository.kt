package com.example.imatah.repository
import com.example.imatah.R
import com.example.imatah.model.Report
import java.util.Date

object ReportRepository {
    fun getReports(): List<Report> {
        return listOf(
            Report(
                id = 1,
                name = "BISKRA 300 CITY",
                description = "\uD83D\uDCCD 456 Downtown Street, Any City",
                status = "Pending",
                category = "General",
                imageUrl = R.drawable.t1.toString().toString(),
                coordinates = Pair(36.8065, 10.1815),
                createdAt = Date(),
                updatedAt = Date()
            ),
            Report(
                id = 2,
                name = "GHARDAIA 700 CITY",
                description = "\uD83D\uDCCD 789 Oasis Road, Any City",
                status = "Completed",
                category = "Urgent",
                imageUrl = R.drawable.t2.toString().toString(),
                coordinates = Pair(36.8065, 10.1815),
                createdAt = Date(),
                updatedAt = Date()
            ),
            Report(
                id = 3,
                name = "ANNABA 400 CITY",
                description = "\uD83D\uDCCD 321 Desert Avenue, Any City",
                status = "In Progress",
                category = "Maintenance",
                imageUrl = R.drawable.t3.toString().toString(),
                coordinates = Pair(36.8065, 10.1815),
                createdAt = Date(),
                updatedAt = Date()
            ),
            Report(
                id = 4,
                name = "ALGER 900 CITY",
                description = "\uD83D\uDCCD 654 Coastal Street, Any City",
                status = "Pending",
                category = "General",
                imageUrl = R.drawable.t1.toString().toString(),
                coordinates = Pair(36.8065, 10.1815),
                createdAt = Date(),
                updatedAt = Date()
            ),
            Report(
                id = 5,
                name = "ORAN 700 CITY",
                description = "\uD83D\uDCCD 987 Harbor Road, Any City",
                status = "Completed",
                category = "Urgent",
                imageUrl = R.drawable.t2.toString(),
                coordinates = Pair(36.8065, 10.1815),
                createdAt = Date(),
                updatedAt = Date()
            )
        )
    }
}

