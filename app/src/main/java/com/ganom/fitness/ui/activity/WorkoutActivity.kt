package com.ganom.fitness.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.ganom.fitness.data.service.WorkoutSession
import com.ganom.fitness.ui.screen.RunningMapScreen
import com.ganom.fitness.ui.theme.FitnessTheme
import com.ganom.fitness.ui.viewmodel.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

/**
 * Owns the GPS workout flow and returns a compact workout summary to MainActivity.
 */
@AndroidEntryPoint
class WorkoutActivity : ComponentActivity() {

    companion object {
        fun createIntent(
            context: Context,
            userId: String,
            selectedDate: String
        ): Intent = Intent(context, WorkoutActivity::class.java).apply {
            putExtra(SharedActivityContract.EXTRA_USER_ID, userId)
            putExtra(SharedActivityContract.EXTRA_SELECTED_DATE, selectedDate)
        }
    }

    private val selectedDate: String by lazy {
        intent.getStringExtra(SharedActivityContract.EXTRA_SELECTED_DATE)
            ?: LocalDate.now().toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
            val isHighContrast by themeViewModel.isHighContrast.collectAsState()

            FitnessTheme(
                darkTheme = isDarkTheme,
                highContrast = isHighContrast
            ) {
                RunningMapScreen(
                    onNavigateBack = { finish() },
                    onWorkoutCompleted = ::finishWithWorkout
                )
            }
        }
    }

    private fun finishWithWorkout(session: WorkoutSession) {
        val durationSeconds = (
            ((session.endTime ?: System.currentTimeMillis()) - session.startTime) / 1000L
        ).toInt()

        val result = Intent().apply {
            putExtra(
                SharedActivityContract.EXTRA_USER_ID,
                intent.getStringExtra(SharedActivityContract.EXTRA_USER_ID).orEmpty()
            )
            putExtra(SharedActivityContract.EXTRA_SELECTED_DATE, selectedDate)
            putExtra(SharedActivityContract.EXTRA_WORKOUT_ID, session.id)
            putExtra(SharedActivityContract.EXTRA_DISTANCE_METRES, session.totalDistance)
            putExtra(SharedActivityContract.EXTRA_DURATION_SECONDS, durationSeconds)
            putExtra(SharedActivityContract.EXTRA_CALORIES, session.caloriesBurned)
        }
        setResult(Activity.RESULT_OK, result)
        finish()
    }
}
