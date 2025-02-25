package com.example.imatah.data.di

import com.example.imatah.data.repository.CategoryRepository
import com.example.imatah.data.repository.CategoryRepositoryImpl
import com.example.imatah.data.repository.ReportRepository
import com.example.imatah.data.repository.ReportRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(): CategoryRepository {
        return CategoryRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideReportRepository(): ReportRepository {
        return ReportRepositoryImpl()
    }
}