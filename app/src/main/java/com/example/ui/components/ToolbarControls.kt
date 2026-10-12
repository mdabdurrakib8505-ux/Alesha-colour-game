package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FitScreen
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ToolType
import com.example.ui.theme.TiaraGold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColoringTopBar(
    title: String,
    canUndo: Boolean,
    canRedo: Boolean,
    isSoundEnabled: Boolean,
    isZenMode: Boolean,
    onBackClick: () -> Unit,
    onUndoClick: () -> Unit,
    onRedoClick: () -> Unit,
    onToggleSound: () -> Unit,
    onToggleZenMode: () -> Unit,
    onResetZoom: () -> Unit,
    onClearCanvas: () -> Unit,
    onSaveClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Princess Studio ✨",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick, modifier = Modifier.testTag("top_back_button")) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to Gallery"
                )
            }
        },
        actions = {
            // Undo
            IconButton(
                onClick = onUndoClick,
                enabled = canUndo,
                modifier = Modifier.testTag("undo_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Undo,
                    contentDescription = "Undo",
                    tint = if (canUndo) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outlineVariant
                )
            }

            // Redo
            IconButton(
                onClick = onRedoClick,
                enabled = canRedo,
                modifier = Modifier.testTag("redo_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Redo,
                    contentDescription = "Redo",
                    tint = if (canRedo) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outlineVariant
                )
            }

            // Sound Toggle
            IconButton(onClick = onToggleSound, modifier = Modifier.testTag("sound_toggle_button")) {
                Icon(
                    imageVector = if (isSoundEnabled) Icons.Default.MusicNote else Icons.Default.MusicOff,
                    contentDescription = if (isSoundEnabled) "Sound On" else "Sound Muted",
                    tint = if (isSoundEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                )
            }

            // Zen Mode Toggle
            IconButton(onClick = onToggleZenMode, modifier = Modifier.testTag("zen_mode_button")) {
                Icon(
                    imageVector = if (isZenMode) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = "Toggle Zen Fullscreen Mode"
                )
            }

            // Reset Zoom
            IconButton(onClick = onResetZoom, modifier = Modifier.testTag("reset_zoom_button")) {
                Icon(
                    imageVector = Icons.Default.FitScreen,
                    contentDescription = "Reset Zoom & Pan"
                )
            }

            // Clear
            IconButton(onClick = onClearCanvas, modifier = Modifier.testTag("clear_canvas_button")) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Clear Canvas"
                )
            }

            // Save
            IconButton(onClick = onSaveClick, modifier = Modifier.testTag("save_artwork_button")) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save Artwork",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            // Share
            IconButton(onClick = onShareClick, modifier = Modifier.testTag("share_artwork_button")) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share Artwork",
                    tint = TiaraGold
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
        ),
        modifier = modifier.shadow(4.dp)
    )
}

@Composable
fun ToolSelectorRow(
    selectedTool: ToolType,
    onToolSelected: (ToolType) -> Unit,
    strokeWidth: Float,
    onStrokeWidthChange: (Float) -> Unit,
    selectedSticker: String,
    onStickerSelected: (String) -> Unit,
    activeColor: Color,
    modifier: Modifier = Modifier
) {
    var showSizeSlider by remember { mutableStateOf(false) }
    var showStickersRow by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        tonalElevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            // Main Tool Buttons Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ToolType.values().forEach { tool ->
                    val isSelected = tool == selectedTool
                    ToolButton(
                        tool = tool,
                        isSelected = isSelected,
                        onClick = {
                            onToolSelected(tool)
                            if (tool == ToolType.STICKER) {
                                showStickersRow = true
                                showSizeSlider = false
                            } else if (tool == ToolType.FILL_BUCKET) {
                                showSizeSlider = false
                                showStickersRow = false
                            } else {
                                showStickersRow = false
                            }
                        }
                    )
                }

                // Size Toggle Button (hidden for Bucket Fill)
                if (selectedTool != ToolType.FILL_BUCKET) {
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (showSizeSlider) MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.surfaceVariant
                            )
                            .clickable {
                                showSizeSlider = !showSizeSlider
                                if (showSizeSlider) showStickersRow = false
                            }
                            .padding(horizontal = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size((strokeWidth / 3.5f).coerceIn(6f, 22f).dp)
                                    .clip(CircleShape)
                                    .background(activeColor)
                            )
                            Text(
                                text = "Size",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Collapsible Stroke Size Slider
            AnimatedVisibility(visible = showSizeSlider && selectedTool != ToolType.FILL_BUCKET) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Brush Size: ${strokeWidth.toInt()}px",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf(8f, 18f, 32f, 50f).forEach { preset ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (strokeWidth == preset) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                    modifier = Modifier.clickable { onStrokeWidthChange(preset) }
                                ) {
                                    Text(
                                        text = "${preset.toInt()}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (strokeWidth == preset) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }
                        }
                    }
                    Slider(
                        value = strokeWidth,
                        onValueChange = onStrokeWidthChange,
                        valueRange = 4f..70f,
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(28.dp)
                            .testTag("stroke_size_slider")
                    )
                }
            }

            // Collapsible Stickers Drawer
            AnimatedVisibility(visible = (showStickersRow || selectedTool == ToolType.STICKER)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                ) {
                    Text(
                        text = "Pick a Princess Stamp to place on canvas:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val stickers = listOf(
                            "👑", "✨", "💎", "🦋", "💖", "⭐", "🪄", "🌹",
                            "🎀", "🦄", "🌸", "🏰", "🐚", "🧜‍♀️", "❄️", "🧁"
                        )
                        stickers.forEach { sticker ->
                            val isSelected = sticker == selectedSticker
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                                    )
                                    .border(
                                        width = if (isSelected) 2.dp else 0.dp,
                                        color = if (isSelected) TiaraGold else Color.Transparent,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { onStickerSelected(sticker) }
                                    .testTag("sticker_stamp_$sticker"),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = sticker, fontSize = 22.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ToolButton(
    tool: ToolType,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
        shadowElevation = if (isSelected) 4.dp else 0.dp,
        modifier = modifier
            .height(42.dp)
            .testTag("tool_button_${tool.name}")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = tool.iconEmoji, fontSize = 16.sp)
            Text(
                text = tool.displayName,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}
