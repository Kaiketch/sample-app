package com.redpond.sampleapp.ui.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val activityResultLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            viewModel.onActivityResult(result)
        }

    val uiState by viewModel.uiState.collectAsState()
    SettingsContent(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onEditClick = viewModel::onEditClick,
        onUploadImageClick = { viewModel.onUploadImageClick(activityResultLauncher) }
    )
}

@Composable
fun SettingsContent(
    uiState: SettingsViewModel.UiState,
    onNameChange: (String) -> Unit,
    onEditClick: () -> Unit,
    onUploadImageClick: () -> Unit
) {
    when (uiState) {
        is SettingsViewModel.UiState.Loading -> {
            SettingsLoadingContent()
        }

        is SettingsViewModel.UiState.Success -> {
            SettingsSuccessContent(
                uiState = uiState,
                onNameChange = onNameChange,
                onEditClick = onEditClick,
                onUploadImageClick = onUploadImageClick
            )
        }

        is SettingsViewModel.UiState.Error -> {
            SettingsErrorContent(
                message = uiState.message
            )
        }
    }
}

@Composable
fun SettingsLoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun SettingsErrorContent(
    message: String
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message);
    }
}

@Composable
fun SettingsSuccessContent(
    modifier: Modifier = Modifier,
    uiState: SettingsViewModel.UiState.Success,
    onNameChange: (String) -> Unit,
    onEditClick: () -> Unit,
    onUploadImageClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text(text = "User: ${uiState.user.name}")

        TextField(value = uiState.user.name, onValueChange = onNameChange)

        Button(onClick = { onEditClick() }) {
            Text(text = "Edit")
        }
        Button(onClick = { onUploadImageClick() }) {
            Text(text = "Upload")
        }
    }
}

