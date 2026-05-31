package com.methodiusdev.lispedu.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    onNavigateToLesson: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToAbout: () -> Unit
) {
    var isLesson1Expanded by remember { mutableStateOf(false) }
    var isMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Learn Common Lisp")
                },
                actions = {
                    IconButton(
                        onClick = { isMenuExpanded = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Open menu"
                        )
                    }

                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Configuration") },
                            onClick = {
                                isMenuExpanded = false
                                onNavigateToSettings()
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Authors") },
                            onClick = {
                                isMenuExpanded = false
                                onNavigateToAbout()
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {

            Column {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isLesson1Expanded = !isLesson1Expanded
                        },
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Lesson 1",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Chapter 4: Lisp Syntax",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        if (isLesson1Expanded) {
                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Learn Common Lisp syntax, lists, atoms, expressions and basic function usage.",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = { onNavigateToLesson() },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF4CAF50)
                                )
                            ) {
                                Text(text = "Start Lesson")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Text(
                        text = "Lesson 2",
                        modifier = Modifier.padding(20.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Text(
                        text = "Lesson 3",
                        modifier = Modifier.padding(20.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(
    showBackground = true
)
@Composable
fun MainMenuScreenPreview() {
    MainMenuScreen(
        onNavigateToLesson = {},
        onNavigateToSettings = {},
        onNavigateToAbout = {}
    )
}