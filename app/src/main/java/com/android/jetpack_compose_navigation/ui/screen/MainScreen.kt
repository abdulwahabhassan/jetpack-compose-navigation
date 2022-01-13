package com.android.jetpack_compose_navigation.ui.screen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions

//Each screen receives the navController and the navStackBackEntry
//1st Screen
@Composable
fun FirstScreen(navController: NavHostController, navBackStackEntry: NavBackStackEntry) {

    //retrieve the data from the arguments in the backStackEntry
    val welcomeText = navBackStackEntry.arguments?.getInt("welcomeText")
    Text("$welcomeText")

    //add the argument to the route to replace the id placeholder
    Button(onClick = { navController.navigate(route = "secondScreen/4") }) {
        Text(text = "Go To Second Screen")
    }

}

//2nd Screen
@Composable
fun SecondScreen(navController: NavHostController, navBackStackEntry: NavBackStackEntry) {

    //retrieve the data from the arguments in the backStackEntry
    val id = navBackStackEntry.arguments?.getInt("id")
    Text("$id")

    Button(onClick = { navController.navigate(route = "thirdScreen/third screen, bye") }) {
        Text(text = "Go To Third Screen")
    }

}

//3rd Screen
@Composable
fun ThirdScreen(navController: NavHostController, navBackStackEntry: NavBackStackEntry) {

    //retrieve the data from the arguments in the backStackEntry
    val text = navBackStackEntry.arguments?.getString("exitText")
    Text("$text")

    Button(onClick = { navController.navigate(route = "firstScreen/welcomeText=Hello from 3rd Screen") }) {
        Text(text = "Go To First Screen")
    }

    Button(onClick = { navController.navigate(route = "fifthScreen") }) {
        Text(text = "Go To Fifth Screen in nestedGraphOne")
    }

}

//4th Screen
@Composable
fun FourthScreen(navController: NavHostController) {

    Text("Fourth Screen")

    Button(onClick = { navController.navigate(route = "fifthScreen") }) {
        Text(text = "Go To Fifth Screen")
    }

}


//5th Screen
@Composable
fun FifthScreen(navController: NavHostController) {

    Text("Fifth Screen")

    Button(onClick = { navController.navigate(route = "fourthScreen"){
        this.launchSingleTop = true //avoiding multiple copies of the same screen on the top of the back stack
        //only navigate to this screen if we are not already there
    } }) {

        Text(text = "Go To Fourth Screen")
    }

}