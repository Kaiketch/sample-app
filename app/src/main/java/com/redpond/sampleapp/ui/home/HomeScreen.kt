@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.redpond.sampleapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.redpond.sampleapp.ui.component.ObserveLifecycleEvent

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    ObserveLifecycleEvent {
        if (it == Lifecycle.Event.ON_START) {
            viewModel.onStart()
        }
    }
    HomeContent(
        uiState = uiState
    )
}

@Composable
fun HomeContent(
    uiState: HomeViewModel.UiState
) {
    Scaffold(topBar = { HomeTopBar() }) { paddingValue ->
        when (uiState) {
            is HomeViewModel.UiState.Loading -> {
                Text(text = "Loading...")
            }

            is HomeViewModel.UiState.Success -> {
                HomeSuccessContent(
                    modifier = Modifier
                        .padding(paddingValue)
                        .fillMaxSize(),
                    uiState = uiState
                )
            }

            is HomeViewModel.UiState.Error -> {
                Text(text = "Error")
            }
        }
    }
}

@Composable
fun HomeTopBar() {
    TopAppBar(colors = topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        actionIconContentColor = MaterialTheme.colorScheme.onSecondary
    ), title = { Text(text = "Home") })
}

@Composable
fun HomeSuccessContent(
    modifier: Modifier = Modifier,
    uiState: HomeViewModel.UiState.Success
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(uiState.users) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(it.imageUrl)
                            .size(Size.ORIGINAL)
                            .build(),
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
                Text(text = it.name)
            }
        }
    }
}