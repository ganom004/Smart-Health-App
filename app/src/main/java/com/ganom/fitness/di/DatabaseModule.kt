package com.ganom.fitness.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ganom.fitness.data.dao.HealthDataDao
import com.ganom.fitness.data.dao.SleepLogDao
import com.ganom.fitness.data.dao.StepLogDao
import com.ganom.fitness.data.dao.UserGoalsDao
import com.ganom.fitness.data.dao.WaterLogDao
import com.ganom.fitness.data.database.HealthDatabase
import com.ganom.fitness.data.repository.HealthRepository
import com.ganom.fitness.data.service.ExportService
import com.ganom.fitness.data.service.FirebaseDataService
import com.ganom.fitness.data.service.GoogleFitService
import com.ganom.fitness.data.service.HealthTipsService
import com.ganom.fitness.data.service.NotificationScheduler
import com.ganom.fitness.data.service.NotificationService
import com.ganom.fitness.data.service.WearableDeviceService
import com.ganom.fitness.ui.viewmodel.ThemeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideHealthDatabase(@ApplicationContext context: Context): HealthDatabase {
        return HealthDatabase.getDatabase(context)
    }

    fun provideUserSpecificDatabase(context: Context, userId: String): HealthDatabase {
        return HealthDatabase.getDatabase(context, userId)
    }

    @Provides
    fun provideHealthDataDao(database: HealthDatabase): HealthDataDao {
        return database.healthDataDao()
    }

    @Provides
    fun provideWaterLogDao(database: HealthDatabase): WaterLogDao {
        return database.waterLogDao()
    }

    @Provides
    fun provideStepLogDao(database: HealthDatabase): StepLogDao {
        return database.stepLogDao()
    }

    @Provides
    fun provideSleepLogDao(database: HealthDatabase): SleepLogDao {
        return database.sleepLogDao()
    }

    @Provides
    fun provideUserGoalsDao(database: HealthDatabase): UserGoalsDao {
        return database.userGoalsDao()
    }

    @Provides
    @Singleton
    fun provideFirebaseDataService(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): FirebaseDataService {
        return FirebaseDataService(firebaseAuth, firestore)
    }

    @Provides
    @Singleton
    fun provideHealthRepository(
        healthDataDao: HealthDataDao,
        waterLogDao: WaterLogDao,
        stepLogDao: StepLogDao,
        sleepLogDao: SleepLogDao,
        userGoalsDao: UserGoalsDao,
        firebaseDataService: FirebaseDataService,
        firebaseAuth: FirebaseAuth
    ): HealthRepository {
        return HealthRepository(
            healthDataDao,
            waterLogDao,
            stepLogDao,
            sleepLogDao,
            userGoalsDao,
            firebaseDataService,
            firebaseAuth
        )
    }

    @Provides
    @Singleton
    fun provideHealthTipsService(): HealthTipsService {
        return HealthTipsService()
    }

    @Provides
    @Singleton
    fun provideExportService(@ApplicationContext context: Context): ExportService {
        return ExportService(context)
    }

    @Provides
    @Singleton
    fun provideNotificationService(@ApplicationContext context: Context): NotificationService {
        return NotificationService(context)
    }

    @Provides
    @Singleton
    fun provideNotificationScheduler(@ApplicationContext context: Context): NotificationScheduler {
        return NotificationScheduler(context)
    }

    @Provides
    @Singleton
    fun provideGoogleFitService(@ApplicationContext context: Context): GoogleFitService {
        return GoogleFitService(context)
    }

    @Provides
    @Singleton
    fun provideWearableDeviceService(@ApplicationContext context: Context): WearableDeviceService {
        return WearableDeviceService(context)
    }

    @Provides
    @Singleton
    fun provideThemeViewModel(@ApplicationContext context: Context): ThemeViewModel {
        return ThemeViewModel(context)
    }
}