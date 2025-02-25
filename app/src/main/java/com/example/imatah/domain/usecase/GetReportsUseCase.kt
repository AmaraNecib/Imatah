package com.example.imatah.domain.usecase

import com.example.imatah.data.repository.ReportRepository
import javax.inject.Inject

class GetReportsUseCase @Inject constructor(private val reportRepository: ReportRepository) {

    operator fun invoke() = reportRepository.getReports()

}