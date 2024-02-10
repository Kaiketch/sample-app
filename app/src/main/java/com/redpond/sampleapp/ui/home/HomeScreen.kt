@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.redpond.sampleapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
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
    Column(
        modifier = modifier
    ) {
        uiState.users.forEach {
            Text(text = it.name)
        }
    }
}