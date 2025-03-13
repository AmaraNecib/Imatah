package com.example.imatah.domain.usecase

import com.example.imatah.data.model.Report
import com.example.imatah.data.repository.ReportRepository
import javax.inject.Inject

class AddReportUseCase @Inject constructor(private val reportRepository: ReportRepository) {


    suspend operator fun invoke(report: Report) = reportRepository.addReport(report)

}
