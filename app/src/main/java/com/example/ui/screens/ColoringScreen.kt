package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.canvas.ColoringCanvas
import com.example.ui.components.ColorPaletteBar
import com.example.ui.components.ColorPickerModal
import com.example.ui.components.ColoringTopBar
import com.example.ui.components.ToolSelectorRow
import com.example.ui.theme.TiaraGold
import com.example.viewmodel.ColoringViewModel

@Composable
fun ColoringScreen(
    viewModel: ColoringViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Standard Android back handler
    BackHandler {
        viewModel.navigateToHome()
    }

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = !state.isZenMode,
                enter = slideInVertically(initialOffsetY = { -it }),
                exit = slideOutVertically(targetOffsetY = { -it })
            ) {
                ColoringTopBar(
                    title = state.selectedPage.title,
                    canUndo = state.undoStack.isNotEmpty(),
                    canRedo = state.redoStack.isNotEmpty(),
                    isSoundEnabled = state.isSoundEnabled,
                    isZenMode = state.isZenMode,
                    onBackClick = { viewModel.navigateToHome() },
                    onUndoClick = { viewModel.undo() },
                    onRedoClick = { viewModel.redo() },
                    onToggleSound = { viewModel.toggleSound() },
                    onToggleZenMode = { viewModel.toggleZenMode() },
                    onResetZoom = { viewModel.resetZoom() },
                    onClearCanvas = { viewModel.showClearDialog() },
                    onSaveClick = { viewModel.saveArtwork(context) },
                    onShareClick = { viewModel.shareCurrentArtwork(context) }
                )
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = !state.isZenMode,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Tool Selector & Size Slider
                    ToolSelectorRow(
                        selectedTool = state.activeTool,
                        onToolSelected = { viewModel.selectTool(it) },
                        strokeWidth = state.strokeWidth,
                        onStrokeWidthChange = { viewModel.setStrokeWidth(it) },
                        selectedSticker = state.selectedSticker,
                        onStickerSelected = { viewModel.selectSticker(it) },
                        activeColor = state.activeColor
                    )

                    // Color Palettes Bar
                    ColorPaletteBar(
                        selectedColor = state.activeColor,
                        onColorSelected = { viewModel.selectColor(it) },
                        onOpenColorLab = { viewModel.openColorLab() },
                        recentColors = state.recentColors
                    )
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Interactive Drawing Canvas
            ColoringCanvas(
                page = state.selectedPage,
                strokes = state.strokes,
                filledRegions = state.filledRegions,
                stickers = state.stickers,
                activeTool = state.activeTool,
                activeColor = state.activeColor,
                strokeWidth = state.strokeWidth,
                selectedSticker = state.selectedSticker,
                zoomScale = state.zoomScale,
                panOffset = state.panOffset,
                onZoomChange = { scale, pan -> viewModel.setZoom(scale, pan) },
                onAddStroke = { stroke -> viewModel.addStroke(stroke) },
                onFillRegion = { regionId, color -> viewModel.fillRegion(regionId, color, context) },
                onAddSticker = { sticker -> viewModel.addSticker(sticker, context) },
                onStrokeFinished = { viewModel.onStrokeFinished(context) },
                modifier = Modifier.fillMaxSize()
            )

            // Zen Mode Exit Button (only shown in Zen mode)
            if (state.isZenMode) {
                FloatingActionButton(
                    onClick = { viewModel.toggleZenMode() },
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                    contentColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .size(46.dp)
                        .testTag("exit_zen_mode_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "Exit Zen Mode"
                    )
                }
            }
        }
    }

    // Custom Color Lab Modal
    if (state.isColorLabOpen) {
        ColorPickerModal(
            initialColor = state.activeColor,
            onColorSelected = { viewModel.selectColor(it) },
            onDismiss = { viewModel.closeColorLab() }
        )
    }

    // Clear Canvas Confirmation Dialog
    if (state.showClearConfirmation) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissClearDialog() },
            title = { Text("Clear Canvas? 🧹", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to clear all your beautiful colors and strokes? You can still undo this action if needed.") },
            confirmButton = {
                Button(
                    onClick = { viewModel.confirmClearCanvas() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.testTag("confirm_clear_button")
                ) {
                    Text("Clear All")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissClearDialog() }) {
                    Text("Keep Coloring")
                }
            }
        )
    }

    // Artwork Saved Celebration Dialog
    state.saveSuccessArtwork?.let { artwork ->
        AlertDialog(
            onDismissRequest = { viewModel.dismissSaveSuccessDialog() },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Masterpiece Saved! 💖✨", fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Your artwork \"${artwork.title}\" has been saved safely to your offline collection.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Would you like to share it with your friends and family?", style = MaterialTheme.typography.bodySmall)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.shareCurrentArtwork(context)
                        viewModel.dismissSaveSuccessDialog()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Text(" Share Now ✨", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissSaveSuccessDialog() }) {
                    Text("Done")
                }
            }
        )
    }
}
