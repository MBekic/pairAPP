package hr.ferit.marinelabekic.myapplication.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hr.ferit.marinelabekic.myapplication.PersonViewModel
import hr.ferit.marinelabekic.myapplication.Routes

@Composable
fun SelectionScreen(viewModel: PersonViewModel, navController: NavController) {
    val currentScreen by remember { mutableStateOf(2) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 25.dp)
    ) {

        BackButton(navController = navController, currentScreen = currentScreen) {}

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            CustomButton(
                navController = navController,
                buttonText = "PRIVATE",
                onClick = {
                    navController.navigate(Routes.PRIVATE_SCREEN)
                    viewModel.shuffleAndAssignRandomIds()
                }
            )

            Spacer(modifier = Modifier.height(30.dp))

            CustomButton(
                navController = navController,
                buttonText = "PUBLIC",
                onClick = {
                    navController.navigate(Routes.PUBLIC_SCREEN)
                }
            )

            Spacer(modifier = Modifier.height(100.dp))

            CustomButton(
                navController = navController,
                backgroundColor = PurpleGray,
                buttonText = "PAIR UP!",
                onClick = {
                    navController.navigate(Routes.PAIR_SCREEN)
                        viewModel.createRandomPairs()
                }
            )
        }
    }
}

