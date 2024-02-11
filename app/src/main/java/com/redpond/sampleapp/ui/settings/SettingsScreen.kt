package com.redpond.sampleapp.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    SettingsContent(
        uiState = uiState,
        onEditClick = viewModel::onEditClick
    )
}

@Composable
fun SettingsContent(
    uiState: SettingsViewModel.UiState,
    onEditClick: (String) -> Unit
) {
    when (uiState) {
        is SettingsViewModel.UiState.Loading -> {
            SettingsLoadingContent()
        }

        is SettingsViewModel.UiState.Success -> {
            SettingsSuccessContent(
                uiState = uiState,
                onEditClick = onEditClick
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

}

@Composable
fun SettingsErrorContent(
    message: String
) {

}

@Composable
fun SettingsSuccessContent(
    modifier: Modifier = Modifier,
    uiState: SettingsViewModel.UiState.Success,
    onEditClick: (String) -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text(text = "User: ${uiState.user.name}")

        Button(onClick = { onEditClick("aws") }) {
            Text(text = "Edit")
        }
    }
}

