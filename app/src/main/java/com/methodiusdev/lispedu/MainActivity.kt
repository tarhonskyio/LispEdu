package com.methodiusdev.lispedu

import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.methodiusdev.lispedu.ui.screens.SplashScreen
import com.methodiusdev.lispedu.notifications.ReminderScheduler

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val preferences = getSharedPreferences("settings", MODE_PRIVATE)
            var isDarkMode by remember {
                mutableStateOf(preferences.getBoolean("dark_mode", false))
            }
            var areLessonRemindersEnabled by remember {
                mutableStateOf(preferences.getBoolean("lesson_reminders", false))
            }
            val lifecycleOwner = LocalLifecycleOwner.current
            val notificationPermissionLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    areLessonRemindersEnabled = true
                    preferences.edit()
                        .putBoolean("lesson_reminders", true)
                        .apply()
                }
            }

            DisposableEffect(lifecycleOwner, areLessonRemindersEnabled) {
                val observer = LifecycleEventObserver { _, event ->
                    when (event) {
                        Lifecycle.Event.ON_START -> {
                            ReminderScheduler.cancelInactivityReminders(this@MainActivity)
                        }

                        Lifecycle.Event.ON_STOP -> {
                            if (areLessonRemindersEnabled) {
                                ReminderScheduler.scheduleInactivityReminders(this@MainActivity)
                            }
                        }

                        else -> Unit
                    }
                }

                lifecycleOwner.lifecycle.addObserver(observer)

                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }

            LispEduTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route
                    ) {
                        composable(route = Screen.Splash.route) {
                            SplashScreen(
                                onSplashFinished = {
                                    navController.navigate(Screen.MainMenu.route) {
                                        popUpTo(Screen.Splash.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }

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
                                },
                                lispRepository = LispRepository
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
                                areLessonRemindersEnabled = areLessonRemindersEnabled,
                                onLessonRemindersChange = { enabled ->
                                    if (enabled) {
                                        if (
                                            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                                            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
                                        ) {
                                            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                        } else {
                                            areLessonRemindersEnabled = true
                                            preferences.edit()
                                                .putBoolean("lesson_reminders", true)
                                                .apply()
                                        }
                                    } else {
                                        areLessonRemindersEnabled = false
                                        preferences.edit()
                                            .putBoolean("lesson_reminders", false)
                                            .apply()
                                        ReminderScheduler.cancelInactivityReminders(this@MainActivity)
                                    }
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
