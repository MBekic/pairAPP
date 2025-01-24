package hr.ferit.marinelabekic.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hr.ferit.marinelabekic.myapplication.ui.theme.InputScreen
import hr.ferit.marinelabekic.myapplication.ui.theme.PairScreen
import hr.ferit.marinelabekic.myapplication.ui.theme.PrivateScreen
import hr.ferit.marinelabekic.myapplication.ui.theme.PublicScreen
import hr.ferit.marinelabekic.myapplication.ui.theme.SelectionScreen
import hr.ferit.marinelabekic.myapplication.ui.theme.ShowPrivate
import hr.ferit.marinelabekic.myapplication.ui.theme.StartScreen

object Routes {
    const val START_SCREEN = "StartScreen"
    const val INPUT_SCREEN = "InputScreen"
    const val SELECTION_SCREEN = "SelectionScreen"
    const val PRIVATE_SCREEN = "PrivateScreen"
    const val PUBLIC_SCREEN = "PublicScreen"
    const val SHOW_PRIVATE = "ShowPrivate"
    const val PAIR_SCREEN = "PairScreen"
}

@Composable
fun NavigationController(
    viewModel: PersonViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.START_SCREEN) {
        composable(Routes.START_SCREEN) {
            StartScreen(
                navController = navController
            )
        }
        composable(Routes.INPUT_SCREEN) {
            InputScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(Routes.SELECTION_SCREEN) {
            SelectionScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(Routes.PRIVATE_SCREEN) {
            PrivateScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(Routes.SHOW_PRIVATE) {
            ShowPrivate(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(Routes.PUBLIC_SCREEN) {
            PublicScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(Routes.PAIR_SCREEN) {
            PairScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}



