package com.android.jetpack_compose_navigation.ui.screen

import androidx.annotation.StringRes
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.android.jetpack_compose_navigation.R

sealed class BottomNavScreen(val route: String, @StringRes val resourceId: Int) {
    object ScreenSix: BottomNavScreen("screenSix", R.string.screen_six)
    object ScreenSeven : BottomNavScreen("screenSeven", R.string.screen_seven)
}

val bottomNavScreens = listOf(
    BottomNavScreen.ScreenSix,
    BottomNavScreen.ScreenSeven
)


//5th Screen
@Composable
fun SixthScreen(navController: NavHostController) {

    Text("Sixth Screen")

}


//5th Screen
@Composable
fun SeventhScreen(navController: NavHostController) {

    Text("Seventh Screen")

}