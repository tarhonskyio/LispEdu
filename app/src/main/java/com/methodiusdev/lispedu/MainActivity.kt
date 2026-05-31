package com.methodiusdev.lispedu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.methodiusdev.lispedu.navigation.Screen
import com.methodiusdev.lispedu.ui.theme.LispEduTheme
import com.methodiusdev.lispedu.ui.screens.MainMenuScreen
import com.methodiusdev.lispedu.ui.screens.AboutScreen
import com.methodiusdev.lispedu.ui.screens.LessonScreen
import androidx.navigation.compose.composable
import com.methodiusdev.lispedu.ui.screens.ConfigScreen
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.methodiusdev.lispedu.data.LispRepository
import com.methodiusdev.lispedu.ui.viewmodel.LessonViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val preferences = getSharedPreferences("settings", MODE_PRIVATE)
            var isDarkMode by remember {
                mutableStateOf(preferences.getBoolean("dark_mode", false))
            }

            LispEduTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.MainMenu.route
                    ) {
                        composable(route = Screen.MainMenu.route) {
                            MainMenuScreen(
                                onNavigateToAbout = {
                                    navController.navigate(Screen.About.route)
                                },
                                onNavigateToSettings = {
                                    navController.navigate(Screen.Settings.route)
                                },
                                onNavigateToLesson = {
                                    navController.navigate(Screen.Lesson.route)
                                }
                            )
                        }

                        composable(route = Screen.About.route) {
                            AboutScreen(
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable(route = Screen.Settings.route) {
                            ConfigScreen(
                                isDarkMode = isDarkMode,
                                onDarkModeChange = { enabled ->
                                    isDarkMode = enabled
                                    preferences.edit()
                                        .putBoolean("dark_mode", enabled)
                                        .apply()
                                },
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable(route = Screen.Lesson.route) {
                            val lessonViewModel: LessonViewModel = viewModel()
                            LessonScreen(
                                viewModel = lessonViewModel,
                                lispRepository = LispRepository,
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onBackToMenu = {
                                    navController.navigate(Screen.MainMenu.route)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
