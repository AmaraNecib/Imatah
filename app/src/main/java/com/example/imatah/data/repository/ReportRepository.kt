package com.example.imatah.data.repository

import com.example.imatah.data.model.Report
import kotlinx.coroutines.flow.Flow

interface ReportRepository {

    fun getReports(): Flow<List<Report>>

    suspend fun addReport(report: Report)

}