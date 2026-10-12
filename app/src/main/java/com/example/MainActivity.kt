package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.ColoringScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.AppScreen
import com.example.viewmodel.ColoringViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val viewModel: ColoringViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsState()

                Surface(modifier = Modifier.fillMaxSize()) {
                    when (uiState.currentScreen) {
                        AppScreen.HOME -> {
                            HomeScreen(
                                savedArtworks = uiState.savedArtworks,
                                onSelectPage = { page -> viewModel.selectPage(page) },
                                onDeleteArtwork = { id -> viewModel.deleteArtwork(this@MainActivity, id) },
                                onLoadArtworks = { context -> viewModel.loadSavedArtworks(context) }
                            )
                        }
                        AppScreen.COLORING -> {
                            ColoringScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}
