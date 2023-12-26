package com.example.imagetotextconverter

sealed class Screen(val route: String){
    object MLKitTextRecognition : Screen("mlKitTextRecognition")
    object PickImageFromGallery : Screen("imageRecognition")
    object lens:Screen("camera")
}
