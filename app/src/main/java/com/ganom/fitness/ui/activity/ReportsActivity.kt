package com.ganom.fitness.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ganom.fitness.ui.screen.ReportsScreen
import com.ganom.fitness.ui.theme.FitnessTheme
import com.ganom.fitness.ui.viewmodel.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

/**
 * Receives a report date/workout ID and loads the full health record from Room.
 */
@AndroidEntryPoint
class ReportsActivity : ComponentActivity() {

    companion object {
        fun createIntent(
            context: Context,
            userId: String,
            selectedDate: String,
            workoutId: String? = null
        ): Intent = Intent(context, ReportsActivity::class.java).apply {
            putExtra(SharedActivityContract.EXTRA_USER_ID, userId)
            putExtra(SharedActivityContract.EXTRA_SELECTED_DATE, selectedDate)
            workoutId?.let {
                putExtra(SharedActivityContract.EXTRA_WORKOUT_ID, it)
            }
        }
    }

    private val selectedDate: String by lazy {
        intent.getStringExtra(SharedActivityContract.EXTRA_SELECTED_DATE)
            ?: LocalDate.now().toString()
    }

    @OptIn(ExperimentalMaterial3Api::class)
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
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Report for $selectedDate") },
                            navigationIcon = {
                                IconButton(onClick = ::finishWithResult) {
                                    Icon(
                                        Icons.Default.ArrowBack,
                                        contentDescription = "Back to dashboard"
                                    )
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        ReportsScreen(
                            reportDate = selectedDate,
                            sharedWorkoutId = intent.getStringExtra(
                                SharedActivityContract.EXTRA_WORKOUT_ID
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        finishWithResult()
    }

    private fun finishWithResult() {
        setResult(
            Activity.RESULT_OK,
            Intent().putExtra(SharedActivityContract.EXTRA_SELECTED_DATE, selectedDate)
        )
        finish()
    }
}
