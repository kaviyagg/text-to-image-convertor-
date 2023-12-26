package com.example.imagetotextconverter

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.sp


@Composable
fun PickImageFromGallery(navController: NavController) {
    val extractedText = remember { mutableStateOf("") }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    // Use LaunchedEffect to launch the image selection when the composable is initially displayed
    LaunchedEffect(key1 = "galleryLaunchKey") {
        // Only launch the gallery if an image has not been selected yet
        if (imageUri == null) {
            launcher.launch("image/*")
        }
    }



    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Check if imageUri is not null
        if (imageUri != null) {
            // Load and process the selected image
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value =
                    MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, imageUri!!)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let { btm ->

                // Display the detected text below the image
                TextRecognitionOnImage(
                    bitmap = btm,
                    extractedText = extractedText,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                )
            }
        }


        Spacer(modifier = Modifier.height(1.dp))
    }
}




@Composable
fun TextRecognitionOnImage(bitmap: Bitmap, extractedText: MutableState<String>, modifier: Modifier) {
    val textRecognizerOptions = TextRecognizerOptions.DEFAULT_OPTIONS
    val textRecognizer = remember { TextRecognition.getClient(textRecognizerOptions) }

    // Process the bitmap using ML Kit Text Recognition
    val image = InputImage.fromBitmap(bitmap, 0)
    textRecognizer.process(image)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                extractedText.value = it.result?.text ?: ""
            }
        }

    // Display the image with recognized text
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        // Use a Column to arrange the image and text vertically
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Image at the top
           Image(
             bitmap = bitmap.asImageBitmap(),
              contentDescription = null,
             modifier = Modifier
                .size(400.dp),
              contentScale = ContentScale.Crop
           )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )
            // Text at the bottom
            Text(
                text = extractedText.value,
                modifier = Modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                color = Color.Black,
                fontSize = 18.sp
            )
        }
    }
}
