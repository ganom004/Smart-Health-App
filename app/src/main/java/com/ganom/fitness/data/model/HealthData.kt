package com.ganom.fitness.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

/**
 * DATA MODELS
 *
 * These are the core data models for the Smart Health Tracker app.
 * They define the structure of health data stored in the Room database.
 *
 * KEY FEATURES:
 * - Room entity annotations for database mapping
 * - Immutable data classes for type safety
 * - Default values for optional fields
 * - Primary keys for database relationships
 * - Date/time handling with ISO strings
 *
 * DATA STRUCTURE:
 * - HealthData: Daily aggregated health metrics
 * - WaterLog: Individual water intake entries
 * - StepLog: Individual step count entries
 * - SleepLog: Individual sleep session entries
 * - UserGoals: User-defined health goals
 */

/**
 * HEALTH DATA ENTITY
 *
 * Represents daily aggregated health metrics for a specific date.
 * This is the main entity that stores calculated health scores and totals.
 *
 * @param date Primary key - Date in YYYY-MM-DD format
 * @param steps Total steps for the day
 * @param distance Total distance covered in meters
 * @param caloriesBurned Total calories burned
 * @param waterIntake Total water intake in ml
 * @param sleepHours Total sleep duration in hours
 * @param heartRate Average heart rate for the day
 * @param healthScore Calculated health score (0-100)
 * @param createdAt ISO datetime string when record was created
 * @param updatedAt ISO datetime string when record was last updated
 */
@Entity(tableName = "health_data")
data class HealthData(
    @PrimaryKey
    val date: String, // YYYY-MM-DD format
    val userId: String = "", // Firebase Auth UID for user isolation
    val steps: Int = 0,
    val distance: Float = 0f, // in meters
    val caloriesBurned: Int = 0,
    val waterIntake: Int = 0, // in ml
    val sleepHours: Float = 0f,
    val heartRate: Int = 0, // average heart rate
    val healthScore: Int = 0, // calculated score 0-100
    val createdAt: String = "", // ISO datetime string
    val updatedAt: String = ""
)

/**
 * WATER LOG ENTITY
 *
 * Represents individual water intake entries.
 * Multiple entries can exist for the same date.
 *
 * @param id Auto-generated primary key
 * @param amount Water amount in ml
 * @param timestamp ISO datetime string when water was consumed
 * @param date Date in YYYY-MM-DD format for aggregation
 */
@Entity(tableName = "water_logs")
data class WaterLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String = "", // Firebase Auth UID for user isolation
    val amount: Int, // in ml
    val timestamp: String, // ISO datetime string
    val date: String // YYYY-MM-DD format
)

/**
 * STEP LOG ENTITY
 *
 * Represents individual step count entries.
 * Multiple entries can exist for the same date.
 *
 * @param id Auto-generated primary key
 * @param steps Number of steps
 * @param timestamp ISO datetime string when steps were recorded
 * @param date Date in YYYY-MM-DD format for aggregation
 */
@Entity(tableName = "step_logs")
data class StepLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String = "", // Firebase Auth UID for user isolation
    val steps: Int,
    val timestamp: String, // ISO datetime string
    val date: String // YYYY-MM-DD format
)

/**
 * SLEEP LOG ENTITY
 *
 * Represents individual sleep session entries.
 * Multiple entries can exist for the same date.
 *
 * @param id Auto-generated primary key
 * @param sleepStart ISO datetime string when sleep started
 * @param sleepEnd ISO datetime string when sleep ended
 * @param duration Sleep duration in hours
 * @param quality Sleep quality rating (0-5 scale)
 * @param date Date in YYYY-MM-DD format for aggregation
 */
@Entity(tableName = "sleep_logs")
data class SleepLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String = "", // Firebase Auth UID for user isolation
    val sleepStart: String, // ISO datetime string
    val sleepEnd: String, // ISO datetime string
    val duration: Float, // in hours
    val quality: Int = 0, // 0-5 scale
    val date: String // YYYY-MM-DD format
)

/**
 * USER GOALS ENTITY
 *
 * Represents user-defined daily health goals.
 * Only one record exists with id = 1.
 *
 * @param id Primary key (always 1)
 * @param dailySteps Daily step goal
 * @param dailyWater Daily water intake goal in ml
 * @param dailySleep Daily sleep goal in hours
 * @param weeklyExercise Weekly exercise goal in minutes
 */
@Entity(tableName = "user_goals")
data class UserGoals(
    @PrimaryKey
    val id: Int = 1,
    val userId: String = "", // Firebase Auth UID for user isolation
    val dailySteps: Int = 10000,
    val dailyWater: Int = 2000, // in ml
    val dailySleep: Float = 8f, // in hours
    val weeklyExercise: Int = 150 // in minutes
)

