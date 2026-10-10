package com.ganom.fitness.ui.navigation

import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ganom.fitness.data.service.NotificationService
import com.ganom.fitness.ui.screen.AccessibilitySettingsScreen
import com.ganom.fitness.ui.screen.ConnectionStatusScreen
import com.ganom.fitness.ui.screen.GoalsScreen
import com.ganom.fitness.ui.screen.GoogleFitSetupScreen
import com.ganom.fitness.ui.screen.ModernDashboardScreen
import com.ganom.fitness.ui.screen.NotificationSettingsScreen
import com.ganom.fitness.ui.screen.ReportsScreen
import com.ganom.fitness.ui.screen.RunningMapScreen
import com.ganom.fitness.ui.screen.SettingsScreen
import com.ganom.fitness.ui.screen.SleepTrackingScreen
import com.ganom.fitness.ui.screen.StepTrackingScreen
import com.ganom.fitness.ui.screen.WaterTrackingScreen
import com.ganom.fitness.ui.screen.WearableSetupScreen

// import com.smarthealthtracker.ui.screen.WorkoutTrackingScreen
// import com.smarthealthtracker.ui.screen.AdvancedChartsScreen

@Composable
fun HealthNavigation(
    navController: NavHostController,
    onSignOut: () -> Unit = {},
    onLaunchWorkout: () -> Unit = {},
    onLaunchReports: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "dashboard",
        modifier = modifier
    ) {
        composable("dashboard") {
            ModernDashboardScreen(
                onNavigateToWater = { navController.navigate("water") },
                onNavigateToSteps = { navController.navigate("steps") },
                onNavigateToSleep = { navController.navigate("sleep") },
                onNavigateToGoals = { navController.navigate("goals") },
                onNavigateToReports = onLaunchReports,
                onNavigateToConnectionStatus = { navController.navigate("connection_status") },
                onNavigateToRunningMap = onLaunchWorkout,
                onSignOut = onSignOut
            )
        }

        composable("water") {
            WaterTrackingScreen()
        }

        composable("steps") {
            StepTrackingScreen()
        }

        composable("running_map") {
            RunningMapScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("sleep") {
            SleepTrackingScreen()
        }

        composable("reports") {
            ReportsScreen()
        }

        composable("settings") {
            SettingsScreen(
                onNavigateToNotifications = { navController.navigate("notifications") },
                onNavigateToGoals = { navController.navigate("goals") },
                onNavigateToAccessibility = { navController.navigate("accessibility") }
            )
        }

        composable("goals") {
            GoalsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("accessibility") {
            AccessibilitySettingsScreen(
                onNavigateToNotifications = { navController.navigate("notifications") },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("notifications") {
            // We'll need to inject NotificationService here
            // For now, let's create a simple version
            NotificationSettingsScreen(
                notificationService = NotificationService(
                    LocalContext.current
                ),
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("connection_status") {
            ConnectionStatusScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToGoogleFitSetup = { navController.navigate("google_fit_setup") },
                onNavigateToWearableSetup = { navController.navigate("wearable_setup") }
            )
        }

        composable("google_fit_setup") {
            GoogleFitSetupScreen(
                onNavigateBack = { navController.popBackStack() },
                onSetupComplete = { navController.popBackStack() }
            )
        }

        composable("wearable_setup") {
            WearableSetupScreen(
                onNavigateBack = { navController.popBackStack() },
                onSetupComplete = { navController.popBackStack() }
            )
        }
    }
}
