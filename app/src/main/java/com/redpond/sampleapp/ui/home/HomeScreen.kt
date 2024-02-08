package com.redpond.sampleapp.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeRoute() {
    HomeScreen()
}

@Composable
fun HomeScreen() {
    Text(text = "Hello, World!")
}

@Preview
@Composable
fun HomePreview() {
    HomeScreen()
}