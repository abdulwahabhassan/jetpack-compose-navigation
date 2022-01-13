package com.android.jetpack_compose_navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.jetpack_compose_navigation.ui.screen.*
import com.android.jetpack_compose_navigation.ui.theme.JetpackComposeNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            JetpackComposeNavigationApp()


        }
    }
}

@Composable
fun JetpackComposeNavigationApp() {
    val navController = rememberNavController()

    JetpackComposeNavigationTheme {
        Scaffold(
            topBar = {},
            bottomBar = {
                BottomAppBar {

                    //Gets the current navigation back stack entry as a MutableState
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination =
                        navBackStackEntry?.destination //the current visible destination

                    bottomNavScreens.forEach { screen ->
                        BottomNavigationItem(
                            //set the selected state by checking if the current destination's hierarchy which generates a sequence of
                            //hierarchical navDestinations contains at least one element and if true, filter the elements and check for
                            //the navDestination whose route is equal to the current destination's route. The bottomNavigationItem associated
                            //with the screen that corresponds to true is selected
                            selected = currentDestination?.hierarchy?.any() { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    //this will prevent building up building up a large stack of destinations
                                    //on the back stack as users select and switch between BottomNavigationItems
                                    this.popUpTo(navController.graph.findStartDestination().id) {
                                        //save the back stack and the state of all destinations
                                        //between the current destination and the
                                        // NavOptionsBuilder.popUpTo ID should be saved for
                                        //later restoration
                                        this.saveState = true
                                    }

                                }
                            },
                            icon = { when (stringResource(id = screen.resourceId)){
                                "Screen Six" ->  Icon(Icons.Filled.Favorite, contentDescription = null)
                                else ->  Icon(Icons.Filled.Home, contentDescription = null)
                            } },
                            label = {
                                Text(
                                text = stringResource(id = screen.resourceId)
                                )
                            }
                        )
                    }
                }

            },
            drawerContent = {}
        ) { paddingValues ->
            JetpackComposeNavigationNavHost(
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }

    }
}

//To add a nested graph to your NavHost, you can use the navigation extension function
//It is strongly recommended that you split your navigation graph into multiple methods as the graph grows in size
fun NavGraphBuilder.nestedGraphOne(navController: NavHostController) { //this creates a new graph that can be nested in a navGraph
    navigation(
        startDestination = "FourthScreenNested",
        route = "nestedNavGraphOne"
    ) {
        composable(route = "fourthScreen") {
            FourthScreen(
                navController = navController
            )

        }

        composable(route = "fifthScreen") {
            FifthScreen(
                navController = navController
            )
        }
    }
}


@Composable
fun JetpackComposeNavigationNavHost(navController: NavHostController, modifier: Modifier) {

    NavHost(
        navController = navController,
        startDestination = "firstScreen?welcomeText={welcomeText}"
    ) {

        //destination 1
        composable(
            route = "firstScreen?welcomeText={welcomeText}", //add an optional argument "welcomeText" to a route
            //optional argument must be included using the query parameter syntax ("?argName={argName}")
            //optional argument must have a defaultValue set, or have nullability = true
            //all optional arguments must be explicitly added to the arguments param of the composable() function
            arguments = listOf(
                navArgument("welcomeText") {
                    this.defaultValue = "Hello, Default Welcome"
                    //this.nullable = true // we could set nullable to true instead of providing a default value
                }
            )
        ) { navBackStackEntry ->

            //pass navController and navBackStackEntry to the screen
            FirstScreen(
                navController = navController,
                navBackStackEntry = navBackStackEntry
            )

        }

        //destination 2
        //By default all arguments are passed as String type, You can specify another type by using the
        //arguments parameter to set a type.
        composable(
            route = "secondScreen/{id}", //define route and it's arguments
            arguments = listOf(
                navArgument(
                    name = "id") {
                    this.type = NavType.IntType //specify the type of this argument
                }
            )
        ) { navBackStackEntry ->

            //pass navController and navBackStackEntry to the screen
            SecondScreen(
                navController = navController,
                navBackStackEntry = navBackStackEntry)

        }

        //destination 3
        composable(
            route = "thirdScreen/{exitText}"
        ) { navBackStackEntry ->

            //pass navController and navBackStackEntry to the screen
            ThirdScreen(navController = navController, navBackStackEntry = navBackStackEntry)

        }

        //bottom navigation destinations
        composable(route = BottomNavScreen.ScreenSix.route) {
            SixthScreen(navController = navController)

        }

        composable(route = BottomNavScreen.ScreenSeven.route) {
            SeventhScreen(navController = navController)

        }

        //nested navGraph
        nestedGraphOne(navController = navController)
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeNavigationTheme {

    }
}