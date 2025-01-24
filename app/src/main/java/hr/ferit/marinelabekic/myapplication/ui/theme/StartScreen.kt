package hr.ferit.marinelabekic.myapplication.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hr.ferit.marinelabekic.myapplication.R
import hr.ferit.marinelabekic.myapplication.Routes

@Composable
fun StartScreen(navController: NavController, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 50.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.pairapp),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(600.dp)
                    .height(400.dp)
            )
            CustomButton(
                navController = navController,
                buttonText = "START",
                onClick = {
                    navController.navigate(Routes.INPUT_SCREEN)
                }
            )
            Text(
                text = "This application pairs individuals from a list.\n" +
                        "Each pair can be displayed either publicly or privately!\n",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 20.dp, start = 25.dp, end = 25.dp)
            )
        }
    }
}

@Composable
fun CustomButton(
    navController: NavController,
    color: Color = Color.Gray,
    backgroundColor: Color = Color.DarkGray,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(defaultElevation = 12.dp),
    buttonText: String = "Button",
    onClick: () -> Unit = {}
) {
    Button(
        contentPadding = PaddingValues(),
        elevation = elevation,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor, contentColor = color),
        shape = RoundedCornerShape(60.dp),
        modifier = Modifier
            .width(160.dp)
            .height(60.dp)
    ) {
        Text(text = buttonText, color = Color.White)
    }
}

