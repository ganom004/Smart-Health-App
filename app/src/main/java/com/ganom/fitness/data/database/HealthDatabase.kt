package com.ganom.fitness.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ganom.fitness.data.dao.HealthDataDao
import com.ganom.fitness.data.dao.SleepLogDao
import com.ganom.fitness.data.dao.StepLogDao
import com.ganom.fitness.data.dao.UserGoalsDao
import com.ganom.fitness.data.dao.WaterLogDao
import com.ganom.fitness.data.model.HealthData
import com.ganom.fitness.data.model.SleepLog
import com.ganom.fitness.data.model.StepLog
import com.ganom.fitness.data.model.UserGoals
import com.ganom.fitness.data.model.WaterLog


@Database(
    entities = [
        HealthData::class,      // Daily health metrics aggregation
        WaterLog::class,        // Individual water intake logs
        StepLog::class,         // Individual step count logs
        SleepLog::class,        // Individual sleep session logs
        UserGoals::class        // User-defined health goals
    ],
    version = 2,                // Database version for migrations (increased for userId fields)
    exportSchema = false        // Disable schema export for production
)
@TypeConverters(Converters::class)
abstract class HealthDatabase : RoomDatabase() {

    // ===== DAO ACCESS METHODS =====
    // These methods provide access to Data Access Objects (DAOs)
    // Each DAO handles CRUD operations for specific entities

    abstract fun healthDataDao(): HealthDataDao    // Daily health data operations
    abstract fun waterLogDao(): WaterLogDao        // Water intake logging
    abstract fun stepLogDao(): StepLogDao          // Step counting operations
    abstract fun sleepLogDao(): SleepLogDao        // Sleep tracking operations
    abstract fun userGoalsDao(): UserGoalsDao      // User goals management

    companion object {
        @Volatile
        private var INSTANCE: HealthDatabase? = null

        /**
         * SINGLETON DATABASE ACCESS
         *
         * This method ensures only one database instance exists throughout the app lifecycle.
         * Uses double-checked locking pattern for thread safety.
         *
         * @param context Application context for database creation
         * @param userId Optional user ID for user-specific database isolation
         * @return HealthDatabase instance
         */
        fun getDatabase(context: Context, userId: String? = null): HealthDatabase {
            val databaseName = if (userId != null) {
                "health_database_$userId"
            } else {
                "health_database"
            }

            return INSTANCE ?: synchronized(this) {
                // Create database instance using Room builder
                val instance = Room.databaseBuilder(
                    context.applicationContext,    // Use application context to prevent memory leaks
                    HealthDatabase::class.java,    // Database class
                    databaseName                    // User-specific database file name
                ).build()
                INSTANCE = instance
                instance
            }
        }

        /**
         * Clear the current database instance
         * This should be called when user logs out to ensure fresh database for next user
         */
        fun clearInstance() {
            synchronized(this) {
                INSTANCE?.close()
                INSTANCE = null
            }
        }
    }
}