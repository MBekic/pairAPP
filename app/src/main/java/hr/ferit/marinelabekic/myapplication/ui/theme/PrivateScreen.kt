package hr.ferit.marinelabekic.myapplication.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hr.ferit.marinelabekic.myapplication.PersonViewModel
import hr.ferit.marinelabekic.myapplication.R
import hr.ferit.marinelabekic.myapplication.Routes

@Composable
fun PrivateScreen(viewModel: PersonViewModel, navController: NavController) {
    val currentScreen by remember { mutableStateOf(3) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 25.dp)
    )
    {
        BackButton(navController = navController, currentScreen = currentScreen) {}
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 75.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.mix),
                contentDescription = "mix",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )

            PrintPrivateScreen(viewModel = viewModel, navController)

        }

        HomeButton(
            navController = navController
        ) {
        }
    }
}

@Composable
fun PrintPrivateScreen(viewModel: PersonViewModel, navController: NavController) {
    val people = viewModel.people

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 75.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .background(
                    color = Color.Black,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                )
                .padding(top = 16.dp, bottom = 0.dp, end = 16.dp, start = 16.dp)
        ) {
            items(people) { person ->
                if (person.name.isNotBlank()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color.DarkGray.copy(alpha = 0.2f),
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                    8.dp
                                )
                            )
                            .padding(all = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = person.name, color = Color.White)
                        Button(
                            onClick = {
                                viewModel.rememberPerson(person)
                                navController.navigate(Routes.SHOW_PRIVATE)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                            shape = RoundedCornerShape(60.dp),
                            modifier = Modifier
                                .width(100.dp)
                                .height(35.dp)
                        ) {
                            Text(text = "SHOW", color = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}




