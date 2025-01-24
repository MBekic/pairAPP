package hr.ferit.marinelabekic.myapplication.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hr.ferit.marinelabekic.myapplication.PersonViewModel
import hr.ferit.marinelabekic.myapplication.R
import hr.ferit.marinelabekic.myapplication.Routes

@Composable
fun InputScreen(viewModel: PersonViewModel, navController: NavController) {
    val currentScreen by remember { mutableStateOf(1) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 25.dp)
    ) {
        BackButton(navController = navController, currentScreen = currentScreen) {}
        /*Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 75.dp, start = 10.dp)
        ) {
            SearchBar(
                viewModel = viewModel,
                iconResource = R.drawable.search,
                labelText = "Search",
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.DarkGray, // Transparentna pozadina unutar TextField-a
                    unfocusedPlaceholderColor = Color.LightGray, // Svijetlo siva za placeholder kada nije fokusiran
                    unfocusedTextColor = Color.White, // Bijela boja za tekst kada nije fokusiran
                    unfocusedLabelColor = Color.Gray, // Siva za labelu kada nije fokusiran
                    focusedIndicatorColor = Color.Transparent, // Bijela boja za indikator kada je fokusiran
                    unfocusedIndicatorColor = Color.Transparent, // Siva boja za indikator kada nije fokusiran
                    disabledIndicatorColor = Color.Transparent // Tamno siva kada je onemogućeno
                )
            )
        }*/
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp)
        ) {

            if (viewModel.people.size < 2) {
                Text(
                    text = "Enter at least 2 people!",
                    color = PurpleGray,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 50.dp)
                )
            }

            NameInput(viewModel = viewModel)

        }
        viewModel.removeInvalidPeople()
        if (viewModel.people.size >= 2) {
            NextButton(
                navController = navController,
                currentScreen = currentScreen,
                iconResource = R.drawable.arr
            ) {
                viewModel.shuffleAndAssignRandomIds()
            }
        }
    }
}

@Composable
fun NameInput(viewModel: PersonViewModel) {
    var nameInput by remember { mutableStateOf(TextFieldValue("")) }
    val people = viewModel.people
    var isNameTaken = viewModel.isNameAlreadyTaken(nameInput.text)
    var showInputField by remember { mutableStateOf(false) }
    var showAddButton by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(end = 16.dp, start = 16.dp, bottom = 75.dp, top = 50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        if (showAddButton) {
            Button(
                onClick = {
                    nameInput = TextFieldValue("")
                    showInputField = true
                    showAddButton = false
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(60.dp),
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
                    .padding(8.dp)
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Icon",
                        tint = Color.Black
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(text = "Add", color = Color.Black)

                }
            }
        }

        if (showInputField) {
            Text(
                text = "Enter Name:",
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 8.sp
                )
            )

            BasicTextField(
                value = nameInput,
                onValueChange = { nameInput = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 0.dp, start = 16.dp, end = 16.dp)
                    .background(
                        Color.White,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp),
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
            )

            if(!isNameTaken){
                Button(
                    onClick = {
                        if (nameInput.text.isNotBlank()) {
                            viewModel.addPerson(nameInput.text)
                            nameInput = TextFieldValue("")
                            showInputField = false
                            showAddButton = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    shape = RoundedCornerShape(60.dp),
                    modifier = Modifier
                        .width(120.dp)
                        .height(50.dp)
                        .padding(8.dp)
                ) {
                    Text(text = "Confirm", color = Color.White)
                }
            }
            else {
                Text(
                    "This name is already taken!",
                    color = PurpleGray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 0.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp, top = 50.dp)
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

            ) {
                items(people) { person ->
                    if (person.name.isNotBlank()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color.DarkGray.copy(alpha = 0.2f),
                                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                                )
                                .padding(all = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = person.name, color = Color.White)
                            Button(
                                onClick = { viewModel.removePerson(person) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                                shape = RoundedCornerShape(60.dp),
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(35.dp)
                            ) {
                                Text(text = "Delete", color = Color.White)
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun NextButton(
    navController: NavController,
    currentScreen: Int,
    @DrawableRes iconResource: Int = R.drawable.arr,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp, end = 20.dp)
    ) {
        NavButton(iconResource, navController = navController) {
            when (currentScreen) {
                1 -> navController.navigate(Routes.SELECTION_SCREEN) // Ovdje nije potrebno mijenjati currentScreen
                3 -> navController.navigate(Routes.START_SCREEN)
                4 -> navController.navigate(Routes.START_SCREEN)
                else -> navController.navigate(Routes.START_SCREEN)
            }
            onClick()
        }
    }
}

@Composable
fun BackButton(
    navController: NavController,
    currentScreen: Int,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 20.dp)
    ) {
        NavButton(R.drawable.backarr, navController = navController) {
            when (currentScreen) {
                1 -> navController.navigate(Routes.START_SCREEN)
                2 -> navController.navigate(Routes.INPUT_SCREEN)
                3 -> navController.navigate(Routes.SELECTION_SCREEN)
                5 -> navController.navigate(Routes.PRIVATE_SCREEN)
                else -> navController.navigate(Routes.START_SCREEN)
            }
            onClick()
        }
    }
}

@Composable
fun NavButton(
    @DrawableRes iconResource: Int,
    navController: NavController,
    color: Color = Color.Gray,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(defaultElevation = 12.dp),
    onClick: () -> Unit = {}
) {
    Button(
        contentPadding = PaddingValues(),
        elevation = elevation,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = color),
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .width(30.dp)
            .height(30.dp)

    ) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    viewModel: PersonViewModel,
    @DrawableRes iconResource: Int,
    labelText: String,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        containerColor = Color.Transparent,
        unfocusedPlaceholderColor = DarkGray,
        unfocusedTextColor = DarkGray,
        //textColor = Color.DarkGray,
        unfocusedLabelColor = DarkGray,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )
) {
    var searchInput by remember {
        mutableStateOf("")
    }
    var searchResults = viewModel.searchPeople(searchInput)
    TextField(
        value = searchInput,
        onValueChange = { searchInput = it },
        label = {
            Text(labelText)
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = iconResource),
                contentDescription = labelText,
                tint = Color.White,
                modifier = Modifier
                    .width(16.dp)
                    .height(16.dp)
            )
        },
        colors = colors,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

