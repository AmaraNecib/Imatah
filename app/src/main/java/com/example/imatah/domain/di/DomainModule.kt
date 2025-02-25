package com.example.imatah.domain.di

import com.example.imatah.data.repository.CategoryRepository
import com.example.imatah.data.repository.ReportRepository
import com.example.imatah.domain.usecase.AddReportUseCase
import com.example.imatah.domain.usecase.GetCategoriesUseCase
import com.example.imatah.domain.usecase.GetReportsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetReportsUseCase(reportRepository: ReportRepository): GetReportsUseCase {
        return GetReportsUseCase(reportRepository)
    }

    @Provides
    @Singleton
    fun provideCategoryUseCase(categoryRepository: CategoryRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(categoryRepository)
    }

    @Provides
    @Singleton
    fun provideAddReportUseCase(reportRepository: ReportRepository): AddReportUseCase {
        return AddReportUseCase(reportRepository)
    }
}