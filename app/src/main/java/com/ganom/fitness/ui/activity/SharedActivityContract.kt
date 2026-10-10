package com.ganom.fitness.ui.activity

/**
 * Keys used when the app's three Android activities share health context.
 *
 * Small identifiers are passed through intents. Complete health records remain
 * in Room and are loaded by the receiving activity.
 */
object SharedActivityContract {
    const val EXTRA_USER_ID = "com.ganom.fitness.extra.USER_ID"
    const val EXTRA_SELECTED_DATE = "com.ganom.fitness.extra.SELECTED_DATE"
    const val EXTRA_WORKOUT_ID = "com.ganom.fitness.extra.WORKOUT_ID"
    const val EXTRA_DISTANCE_METRES = "com.ganom.fitness.extra.DISTANCE_METRES"
    const val EXTRA_DURATION_SECONDS = "com.ganom.fitness.extra.DURATION_SECONDS"
    const val EXTRA_CALORIES = "com.ganom.fitness.extra.CALORIES"
}
