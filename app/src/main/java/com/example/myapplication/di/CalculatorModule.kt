package com.example.myapplication.di

import com.example.myapplication.model.CalculatorRepo
import com.example.myapplication.model.CalculatorRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CalculatorModule {

    @Provides
    @Singleton
    fun provideCalculatorRepo(): CalculatorRepo {
        return CalculatorRepoImpl()
    }
}