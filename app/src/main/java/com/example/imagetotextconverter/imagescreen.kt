package com.example.imagetotextconverter


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MLKitTextRecognition(navController: NavController) {
    var showImageRecognition by remember { mutableStateOf(false) }
    var showTextRecognition by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Button to open the gallery
            RoundedButton("Scan From Image", onClick = {
                // Handle Scan From Image
                showImageRecognition = true
            })

            Spacer(modifier = Modifier.height(20.dp))

            // Button to open the gallery and navigate to the next page
            RoundedButton("Live scan", onClick = {
                // Open the gallery
                showTextRecognition = true
            })
        }
        if(showTextRecognition){
            navController.navigate("camera");
            showTextRecognition = false
        }
        if (showImageRecognition) {
            navController.navigate("imageRecognition");
            showImageRecognition = false
        }
    }
}


