package hr.ferit.marinelabekic.myapplication.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hr.ferit.marinelabekic.myapplication.PersonViewModel
import hr.ferit.marinelabekic.myapplication.R
import hr.ferit.marinelabekic.myapplication.Routes

@Composable
fun ShowPrivate(viewModel: PersonViewModel, navController: NavController) {
    val currentScreen by remember { mutableStateOf(5) }

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

            PrintShowScreen(viewModel = viewModel)
        }

        HomeButton(
            navController = navController
        ) {
        }
    }
}

@Composable
fun PrintShowScreen(viewModel: PersonViewModel) {
    val people = viewModel.people
    val target = viewModel.rememberedPerson

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 75.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if(target != null) {
            Column (
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .background(
                        color = Color.Black,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                    )
                    .padding(top = 16.dp, bottom = 0.dp, end = 16.dp, start = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.DarkGray.copy(alpha = 0.2f),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                        )
                        .padding(all = 8.dp)
                        .height(30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        val personById = people.find { it.id == target.id }
                        Text(
                            text = personById?.name ?: "No name",
                            color = Color.White,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.arr),
                            contentDescription = "Arrow Image",
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        val personByPairId = people.find { it.id == target.pairId }
                        Text(
                            text = personByPairId?.name ?: "No name",
                            color = Color.White,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeButton(
    navController: NavController,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp, end = 20.dp)
    ) {
        Button(
            onClick = {
                navController.navigate(Routes.START_SCREEN)
                onClick()
            },
            modifier = Modifier
                .background(Color.Black),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
        ) {
            Text(
                text = "HOME",
                color = Color.White,
                fontFamily = FontFamily.Default,
                //fontWeight = FontWeight.Bold
            )
        }
    }
}
