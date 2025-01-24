package hr.ferit.marinelabekic.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import hr.ferit.marinelabekic.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<PersonViewModel>()
        setContent {
            MyApplicationTheme {
                NavigationController(viewModel)
            }
        }
    }
}
