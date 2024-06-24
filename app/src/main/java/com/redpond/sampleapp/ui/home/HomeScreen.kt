@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.redpond.sampleapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.redpond.sampleapp.domain.model.User
import com.redpond.sampleapp.ui.component.ObserveLifecycleEvent
import com.redpond.sampleapp.util.formatToJpDateTime

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
        uiState = uiState,
        onSortClick = viewModel::onLocalSortClick
    )
}

@Composable
fun HomeContent(
    uiState: HomeViewModel.UiState,
    onSortClick: (HomeViewModel.LocalSortType) -> Unit
) {
    Scaffold(topBar = {
        HomeTopBar(
            onSortClick = onSortClick
        )
    }) { paddingValue ->
        when (uiState) {
            is HomeViewModel.UiState.Loading -> {
                HomeLoadingContent()
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
                HomeErrorContent(uiState = uiState)
            }
        }
    }
}

@Composable
fun HomeTopBar(
    onSortClick: (HomeViewModel.LocalSortType) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        ),
        title = { Text(text = "Home") },
        actions = {
            IconButton(
                onClick = {
                isExpanded = true
            }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                    DropdownMenuItem(text = {
                        Text(text = "Sort ASC")
                    }, onClick = { onSortClick(HomeViewModel.LocalSortType.ID_ASC) })
                    DropdownMenuItem(text = {
                        Text(text = "Sort DESC")
                    }, onClick = { onSortClick(HomeViewModel.LocalSortType.ID_DESC) })
                }
            }
        })
}

@Composable
fun HomeLoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun HomeErrorContent(
    uiState: HomeViewModel.UiState.Error
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = uiState.message);
    }
}

@Composable
fun HomeSuccessContent(
    modifier: Modifier = Modifier,
    uiState: HomeViewModel.UiState.Success
) {
    val state = rememberLazyListState()
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = state
    ) {
        items(uiState.users) {
            HomeListItem(user = it)
        }
    }
}

@Composable
fun HomeListItem(
    modifier: Modifier = Modifier,
    user: User,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.imageUrl)
                    .size(Size.ORIGINAL)
                    .build(),
            ),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Text(
            text = "${user.id} ${user.name} ${
                user.created?.formatToJpDateTime()
            }"
        )
    }
}