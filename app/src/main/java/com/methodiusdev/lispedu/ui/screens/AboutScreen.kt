package com.methodiusdev.lispedu.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.methodiusdev.lispedu.R
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import android.webkit.WebView
import android.webkit.WebViewClient

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun AboutScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.ideis_logo),
            contentDescription = "IDEIS Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        val htmlMapPreview = """
            <iframe style="border:none" src="https://mapy.com/s/carulabako" width="400" height="280" frameborder="0"></iframe>
        """.trimIndent()
        
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    loadData(htmlMapPreview, "text/html", "utf-8")
                }
            },
            modifier = Modifier.fillMaxWidth().height(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Authors:")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Oleksandr Tarhonskyi")
        Text(text = "Kyrylo Zhulin")

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
fun AboutScreenPreview() {
    AboutScreen(
        onBackClick = {}
    )
}