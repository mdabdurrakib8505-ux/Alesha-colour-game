package com.example.model

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import java.util.UUID

enum class ToolType(val displayName: String, val iconEmoji: String) {
    PEN("Magic Pen", "✏️"),
    WATERCOLOR("Soft Brush", "🖌️"),
    SPRAY("Spray Paint", "💨"),
    SPARKLE("Sparkle Wand", "✨"),
    NEON("Neon Glow", "💡"),
    FILL_BUCKET("Tap to Fill", "🪣"),
    ERASER("Eraser", "🧹"),
    STICKER("Stickers", "👑")
}

data class SprayDot(
    val x: Float,
    val y: Float,
    val radius: Float,
    val alpha: Float
)

data class SparkleDot(
    val x: Float,
    val y: Float,
    val size: Float,
    val rotation: Float,
    val colorArgb: Long
)

data class Stroke(
    val id: String = UUID.randomUUID().toString(),
    val tool: ToolType,
    val colorArgb: Long,
    val strokeWidth: Float,
    val alpha: Float = 1.0f,
    val points: List<Offset> = emptyList(),
    val sprayDots: List<SprayDot> = emptyList(),
    val sparkles: List<SparkleDot> = emptyList()
) {
    val color: Color get() = Color(colorArgb)
}

data class StickerStamp(
    val id: String = UUID.randomUUID().toString(),
    val symbol: String,
    val x: Float,
    val y: Float,
    val size: Float = 48f
)

sealed interface UndoAction {
    data class AddStroke(val stroke: Stroke) : UndoAction
    data class FillRegion(val regionId: String, val previousColor: Long?, val newColor: Long) : UndoAction
    data class AddSticker(val sticker: StickerStamp) : UndoAction
    data class ClearCanvas(
        val previousStrokes: List<Stroke>,
        val previousFills: Map<String, Long>,
        val previousStickers: List<StickerStamp>
    ) : UndoAction
}

data class RegionPathData(
    val id: String,
    val name: String,
    val pathCommands: String,
    val defaultColorArgb: Long? = null
)

data class OutlinePathData(
    val pathCommands: String,
    val strokeWidth: Float = 3f,
    val isClosed: Boolean = false
)

data class PrincessPage(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val emoji: String,
    val difficulty: String = "All Ages",
    val regions: List<RegionPathData> = emptyList(),
    val outlines: List<OutlinePathData> = emptyList(),
    val isBlankCanvas: Boolean = false
)

data class SavedArtwork(
    val id: String,
    val pageId: String,
    val title: String,
    val timestamp: Long,
    val thumbnailFileName: String,
    val strokesCount: Int,
    val filledCount: Int
)

data class CanvasSparkle(
    val id: String = UUID.randomUUID().toString(),
    val x: Float,
    val y: Float,
    val size: Float,
    val color: Color,
    val durationMs: Int = 800
)
