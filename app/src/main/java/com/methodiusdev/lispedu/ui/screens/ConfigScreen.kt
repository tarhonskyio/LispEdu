package com.methodiusdev.lispedu.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ConfigScreen(
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    onBackClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Configuration")

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Dark Mode")

            Spacer(modifier = Modifier.width(16.dp))

            Switch(
                checked = isDarkMode,
                onCheckedChange = onDarkModeChange
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onBackClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            )
        ) {
            Text(text = "Back")
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(
    showBackground = true
)
@Composable
fun ConfigScreenPreview() {
    ConfigScreen(
        isDarkMode = false,
        onDarkModeChange = {},
        onBackClick = {}
    )
}
