package com.example.imatah.data.repository

import com.example.imatah.data.model.Report
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.Date
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(): ReportRepository {

    private val _reports = mutableListOf(
        Report(
            id = 1,
            name = "Report 1",
            description = "This is the first report",
            status = "Pending",
            category = "General",
            imageUrl = "https://images.pexels.com/photos/14454209/pexels-photo-14454209.jpeg?auto=compress&cs=tinysrgb&w=600&lazy=load",
            coordinates = Pair(36.8065, 10.1815),
            createdAt = Date(),
            updatedAt = Date()
        ),
        Report(
            id = 2,
            name = "Report 2",
            description = "This is the second report",
            status = "Completed",
            category = "Urgent",
            imageUrl = "https://picsum.photos/id/238/600/400",
            coordinates = Pair(36.8065, 10.1815),
            createdAt = Date(),
            updatedAt = Date()
        ),
        Report(
            id = 3,
            name = "Report 3",
            description = "This is the third report",
            status = "In Progress",
            category = "Maintenance",
            imageUrl = "https://images.pexels.com/photos/30641145/pexels-photo-30641145/free-photo-of-stunning-aerial-view-of-cape-town-beach-waves.jpeg?auto=compress&cs=tinysrgb&w=600&lazy=load",
            coordinates = Pair(36.8065, 10.1815),
            createdAt = Date(),
            updatedAt = Date()
        ),
        Report(
            id = 4,
            name = "Report 4",
            description = "This is the fourth report",
            status = "Pending",
            category = "General",
            imageUrl = "https://picsum.photos/id/240/600/400",
            coordinates = Pair(36.8065, 10.1815),
            createdAt = Date(),
            updatedAt = Date()
        ),
        Report(
            id = 5,
            name = "Report 5",
            description = "This is the fifth report",
            status = "Completed",
            category = "Urgent",
            imageUrl = "https://picsum.photos/id/241/600/400",
            coordinates = Pair(36.8065, 10.1815),
            createdAt = Date(),
            updatedAt = Date()
        )
    )

    private val _reportFlow = MutableSharedFlow<List<Report>>(replay = 1)

    init{

        _reportFlow.tryEmit(_reports.toList())

    }

    override fun getReports(): Flow<List<Report>> = _reportFlow

    override suspend fun addReport(report: Report) {
        _reports.add(report)
        _reportFlow.emit(_reports.toList())
    }
}
