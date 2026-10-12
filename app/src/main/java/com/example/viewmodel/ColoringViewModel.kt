package com.example.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.PathParser
import com.example.data.PrincessPagesData
import com.example.model.ColorPalettes
import com.example.model.PrincessPage
import com.example.model.SavedArtwork
import com.example.model.StickerStamp
import com.example.model.Stroke
import com.example.model.ToolType
import com.example.model.UndoAction
import com.example.util.ArtworkStorage
import com.example.util.SoundEffects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

enum class AppScreen {
    HOME,
    COLORING
}

data class ColoringUiState(
    val currentScreen: AppScreen = AppScreen.HOME,
    val selectedPage: PrincessPage = PrincessPagesData.royalPrincessPage,
    val activeTool: ToolType = ToolType.PEN,
    val activeColor: Color = ColorPalettes.defaultSelectedColor,
    val strokeWidth: Float = 16f,
    val selectedSticker: String = "👑",
    val strokes: List<Stroke> = emptyList(),
    val filledRegions: Map<String, Long> = emptyMap(),
    val stickers: List<StickerStamp> = emptyList(),
    val undoStack: List<UndoAction> = emptyList(),
    val redoStack: List<UndoAction> = emptyList(),
    val recentColors: List<Color> = emptyList(),
    val isSoundEnabled: Boolean = true,
    val isZenMode: Boolean = false,
    val zoomScale: Float = 1.0f,
    val panOffset: Offset = Offset.Zero,
    val savedArtworks: List<SavedArtwork> = emptyList(),
    val isColorLabOpen: Boolean = false,
    val showClearConfirmation: Boolean = false,
    val saveSuccessArtwork: SavedArtwork? = null
)

class ColoringViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ColoringUiState())
    val uiState: StateFlow<ColoringUiState> = _uiState.asStateFlow()

    fun selectPage(page: PrincessPage) {
        _uiState.update {
            it.copy(
                selectedPage = page,
                currentScreen = AppScreen.COLORING,
                strokes = emptyList(),
                filledRegions = emptyMap(),
                stickers = emptyList(),
                undoStack = emptyList(),
                redoStack = emptyList(),
                zoomScale = 1.0f,
                panOffset = Offset.Zero,
                isZenMode = false
            )
        }
    }

    fun navigateToHome() {
        _uiState.update { it.copy(currentScreen = AppScreen.HOME, isZenMode = false) }
    }

    fun selectTool(tool: ToolType) {
        _uiState.update { it.copy(activeTool = tool) }
    }

    fun selectColor(color: Color) {
        _uiState.update { state ->
            val updatedRecents = (listOf(color) + state.recentColors.filter { it.toArgb() != color.toArgb() }).take(10)
            state.copy(activeColor = color, recentColors = updatedRecents)
        }
    }

    fun setStrokeWidth(width: Float) {
        _uiState.update { it.copy(strokeWidth = width) }
    }

    fun selectSticker(sticker: String) {
        _uiState.update { it.copy(selectedSticker = sticker, activeTool = ToolType.STICKER) }
    }

    fun addStroke(stroke: Stroke) {
        _uiState.update { state ->
            state.copy(
                strokes = state.strokes + stroke,
                undoStack = state.undoStack + UndoAction.AddStroke(stroke),
                redoStack = emptyList()
            )
        }
    }

    fun fillRegion(regionId: String, colorArgb: Long, context: Context) {
        val previousColor = _uiState.value.filledRegions[regionId]
        if (previousColor == colorArgb) return

        SoundEffects.playFillBubble(context, _uiState.value.isSoundEnabled)
        SoundEffects.performLightHaptic(context)

        _uiState.update { state ->
            val updatedFills = state.filledRegions + (regionId to colorArgb)
            state.copy(
                filledRegions = updatedFills,
                undoStack = state.undoStack + UndoAction.FillRegion(regionId, previousColor, colorArgb),
                redoStack = emptyList()
            )
        }
    }

    fun addSticker(sticker: StickerStamp, context: Context) {
        SoundEffects.playFillBubble(context, _uiState.value.isSoundEnabled)
        SoundEffects.performLightHaptic(context)

        _uiState.update { state ->
            state.copy(
                stickers = state.stickers + sticker,
                undoStack = state.undoStack + UndoAction.AddSticker(sticker),
                redoStack = emptyList()
            )
        }
    }

    fun onStrokeFinished(context: Context) {
        SoundEffects.playSparkleChime(context, _uiState.value.isSoundEnabled)
        SoundEffects.performLightHaptic(context)
    }

    fun undo() {
        val state = _uiState.value
        if (state.undoStack.isEmpty()) return

        val lastAction = state.undoStack.last()
        val newUndoStack = state.undoStack.dropLast(1)
        val newRedoStack = state.redoStack + lastAction

        when (lastAction) {
            is UndoAction.AddStroke -> {
                _uiState.update {
                    it.copy(
                        strokes = it.strokes.filterNot { s -> s.id == lastAction.stroke.id },
                        undoStack = newUndoStack,
                        redoStack = newRedoStack
                    )
                }
            }
            is UndoAction.FillRegion -> {
                _uiState.update {
                    val updatedFills = if (lastAction.previousColor != null) {
                        it.filledRegions + (lastAction.regionId to lastAction.previousColor)
                    } else {
                        it.filledRegions - lastAction.regionId
                    }
                    it.copy(
                        filledRegions = updatedFills,
                        undoStack = newUndoStack,
                        redoStack = newRedoStack
                    )
                }
            }
            is UndoAction.AddSticker -> {
                _uiState.update {
                    it.copy(
                        stickers = it.stickers.filterNot { s -> s.id == lastAction.sticker.id },
                        undoStack = newUndoStack,
                        redoStack = newRedoStack
                    )
                }
            }
            is UndoAction.ClearCanvas -> {
                _uiState.update {
                    it.copy(
                        strokes = lastAction.previousStrokes,
                        filledRegions = lastAction.previousFills,
                        stickers = lastAction.previousStickers,
                        undoStack = newUndoStack,
                        redoStack = newRedoStack
                    )
                }
            }
        }
    }

    fun redo() {
        val state = _uiState.value
        if (state.redoStack.isEmpty()) return

        val actionToRedo = state.redoStack.last()
        val newRedoStack = state.redoStack.dropLast(1)
        val newUndoStack = state.undoStack + actionToRedo

        when (actionToRedo) {
            is UndoAction.AddStroke -> {
                _uiState.update {
                    it.copy(
                        strokes = it.strokes + actionToRedo.stroke,
                        undoStack = newUndoStack,
                        redoStack = newRedoStack
                    )
                }
            }
            is UndoAction.FillRegion -> {
                _uiState.update {
                    it.copy(
                        filledRegions = it.filledRegions + (actionToRedo.regionId to actionToRedo.newColor),
                        undoStack = newUndoStack,
                        redoStack = newRedoStack
                    )
                }
            }
            is UndoAction.AddSticker -> {
                _uiState.update {
                    it.copy(
                        stickers = it.stickers + actionToRedo.sticker,
                        undoStack = newUndoStack,
                        redoStack = newRedoStack
                    )
                }
            }
            is UndoAction.ClearCanvas -> {
                _uiState.update {
                    it.copy(
                        strokes = emptyList(),
                        filledRegions = emptyMap(),
                        stickers = emptyList(),
                        undoStack = newUndoStack,
                        redoStack = newRedoStack
                    )
                }
            }
        }
    }

    fun showClearDialog() {
        _uiState.update { it.copy(showClearConfirmation = true) }
    }

    fun dismissClearDialog() {
        _uiState.update { it.copy(showClearConfirmation = false) }
    }

    fun confirmClearCanvas() {
        val state = _uiState.value
        if (state.strokes.isEmpty() && state.filledRegions.isEmpty() && state.stickers.isEmpty()) {
            _uiState.update { it.copy(showClearConfirmation = false) }
            return
        }

        val clearAction = UndoAction.ClearCanvas(
            previousStrokes = state.strokes,
            previousFills = state.filledRegions,
            previousStickers = state.stickers
        )

        _uiState.update {
            it.copy(
                strokes = emptyList(),
                filledRegions = emptyMap(),
                stickers = emptyList(),
                undoStack = it.undoStack + clearAction,
                redoStack = emptyList(),
                showClearConfirmation = false
            )
        }
    }

    fun toggleSound() {
        _uiState.update { it.copy(isSoundEnabled = !it.isSoundEnabled) }
    }

    fun toggleZenMode() {
        _uiState.update { it.copy(isZenMode = !it.isZenMode) }
    }

    fun setZoom(scale: Float, pan: Offset) {
        _uiState.update { it.copy(zoomScale = scale, panOffset = pan) }
    }

    fun resetZoom() {
        _uiState.update { it.copy(zoomScale = 1.0f, panOffset = Offset.Zero) }
    }

    fun openColorLab() {
        _uiState.update { it.copy(isColorLabOpen = true) }
    }

    fun closeColorLab() {
        _uiState.update { it.copy(isColorLabOpen = false) }
    }

    fun dismissSaveSuccessDialog() {
        _uiState.update { it.copy(saveSuccessArtwork = null) }
    }

    fun loadSavedArtworks(context: Context) {
        viewModelScope.launch {
            val list = ArtworkStorage.loadAllSavedArtworks(context)
            _uiState.update { it.copy(savedArtworks = list) }
        }
    }

    fun deleteArtwork(context: Context, id: String) {
        viewModelScope.launch {
            ArtworkStorage.deleteArtwork(context, id)
            loadSavedArtworks(context)
        }
    }

    fun saveArtwork(context: Context, renderWidth: Int = 800, renderHeight: Int = 800) {
        val state = _uiState.value
        val bitmap = Bitmap.createBitmap(renderWidth, renderHeight, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bitmap)

        // Draw background
        canvas.drawColor(android.graphics.Color.WHITE)

        val wF = renderWidth.toFloat()
        val hF = renderHeight.toFloat()

        // 1. Draw Fills
        if (!state.selectedPage.isBlankCanvas) {
            val fillPaint = android.graphics.Paint().apply {
                isAntiAlias = true
                style = android.graphics.Paint.Style.FILL
            }
            state.selectedPage.regions.forEach { region ->
                val cLong = state.filledRegions[region.id] ?: region.defaultColorArgb
                if (cLong != null) {
                    fillPaint.color = cLong.toInt()
                    val p = PathParser.buildScaledPath(region.pathCommands, wF, hF)
                    canvas.drawPath(p, fillPaint)
                }
            }
        }

        // 2. Draw Strokes
        state.strokes.forEach { stroke ->
            val strokePaint = android.graphics.Paint().apply {
                isAntiAlias = true
                color = stroke.colorArgb.toInt()
                alpha = (stroke.alpha * 255).toInt()
                strokeWidth = stroke.strokeWidth * (renderWidth / 800f)
                strokeCap = android.graphics.Paint.Cap.ROUND
                strokeJoin = android.graphics.Paint.Join.ROUND
                style = android.graphics.Paint.Style.STROKE
            }

            if (stroke.tool == ToolType.SPRAY) {
                strokePaint.style = android.graphics.Paint.Style.FILL
                stroke.sprayDots.forEach { dot ->
                    strokePaint.alpha = (dot.alpha * 255).toInt()
                    canvas.drawCircle(dot.x * (renderWidth / 800f), dot.y * (renderHeight / 800f), dot.radius, strokePaint)
                }
            } else if (stroke.points.size > 1) {
                val p = android.graphics.Path()
                val p0 = stroke.points[0]
                p.moveTo(p0.x * (renderWidth / 800f), p0.y * (renderHeight / 800f))
                for (i in 1 until stroke.points.size) {
                    val prev = stroke.points[i - 1]
                    val curr = stroke.points[i]
                    val mx = ((prev.x + curr.x) / 2f) * (renderWidth / 800f)
                    val my = ((prev.y + curr.y) / 2f) * (renderHeight / 800f)
                    p.quadTo(prev.x * (renderWidth / 800f), prev.y * (renderHeight / 800f), mx, my)
                }
                canvas.drawPath(p, strokePaint)
            }
        }

        // 3. Draw Outlines
        if (!state.selectedPage.isBlankCanvas) {
            val outlinePaint = android.graphics.Paint().apply {
                isAntiAlias = true
                color = android.graphics.Color.rgb(40, 28, 48)
                strokeCap = android.graphics.Paint.Cap.ROUND
                strokeJoin = android.graphics.Paint.Join.ROUND
                style = android.graphics.Paint.Style.STROKE
            }
            state.selectedPage.outlines.forEach { ol ->
                outlinePaint.strokeWidth = ol.strokeWidth * (renderWidth / 1000f).coerceAtLeast(1f)
                val p = PathParser.buildScaledPath(ol.pathCommands, wF, hF)
                canvas.drawPath(p, outlinePaint)
            }
        }

        // 4. Draw Stickers
        val textPaint = android.graphics.Paint().apply {
            isAntiAlias = true
            textAlign = android.graphics.Paint.Align.CENTER
        }
        state.stickers.forEach { sticker ->
            textPaint.textSize = sticker.size * (renderWidth / 800f)
            canvas.drawText(
                sticker.symbol,
                sticker.x * (renderWidth / 800f),
                (sticker.y + sticker.size / 3f) * (renderHeight / 800f),
                textPaint
            )
        }

        val artworkId = UUID.randomUUID().toString()
        val saved = ArtworkStorage.saveArtworkBitmap(
            context = context,
            id = artworkId,
            pageId = state.selectedPage.id,
            title = state.selectedPage.title,
            bitmap = bitmap,
            strokesCount = state.strokes.size,
            filledCount = state.filledRegions.size
        )

        if (saved != null) {
            _uiState.update { it.copy(saveSuccessArtwork = saved) }
            loadSavedArtworks(context)
        }
    }

    fun shareCurrentArtwork(context: Context, renderWidth: Int = 800, renderHeight: Int = 800) {
        val state = _uiState.value
        val bitmap = Bitmap.createBitmap(renderWidth, renderHeight, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bitmap)
        canvas.drawColor(android.graphics.Color.WHITE)
        val wF = renderWidth.toFloat()
        val hF = renderHeight.toFloat()

        if (!state.selectedPage.isBlankCanvas) {
            val fillPaint = android.graphics.Paint().apply {
                isAntiAlias = true
                style = android.graphics.Paint.Style.FILL
            }
            state.selectedPage.regions.forEach { region ->
                val cLong = state.filledRegions[region.id] ?: region.defaultColorArgb
                if (cLong != null) {
                    fillPaint.color = cLong.toInt()
                    val p = PathParser.buildScaledPath(region.pathCommands, wF, hF)
                    canvas.drawPath(p, fillPaint)
                }
            }
        }

        state.strokes.forEach { stroke ->
            val strokePaint = android.graphics.Paint().apply {
                isAntiAlias = true
                color = stroke.colorArgb.toInt()
                alpha = (stroke.alpha * 255).toInt()
                strokeWidth = stroke.strokeWidth * (renderWidth / 800f)
                strokeCap = android.graphics.Paint.Cap.ROUND
                strokeJoin = android.graphics.Paint.Join.ROUND
                style = android.graphics.Paint.Style.STROKE
            }
            if (stroke.tool == ToolType.SPRAY) {
                strokePaint.style = android.graphics.Paint.Style.FILL
                stroke.sprayDots.forEach { dot ->
                    strokePaint.alpha = (dot.alpha * 255).toInt()
                    canvas.drawCircle(dot.x * (renderWidth / 800f), dot.y * (renderHeight / 800f), dot.radius, strokePaint)
                }
            } else if (stroke.points.size > 1) {
                val p = android.graphics.Path()
                val p0 = stroke.points[0]
                p.moveTo(p0.x * (renderWidth / 800f), p0.y * (renderHeight / 800f))
                for (i in 1 until stroke.points.size) {
                    val prev = stroke.points[i - 1]
                    val curr = stroke.points[i]
                    val mx = ((prev.x + curr.x) / 2f) * (renderWidth / 800f)
                    val my = ((prev.y + curr.y) / 2f) * (renderHeight / 800f)
                    p.quadTo(prev.x * (renderWidth / 800f), prev.y * (renderHeight / 800f), mx, my)
                }
                canvas.drawPath(p, strokePaint)
            }
        }

        if (!state.selectedPage.isBlankCanvas) {
            val outlinePaint = android.graphics.Paint().apply {
                isAntiAlias = true
                color = android.graphics.Color.rgb(40, 28, 48)
                strokeCap = android.graphics.Paint.Cap.ROUND
                strokeJoin = android.graphics.Paint.Join.ROUND
                style = android.graphics.Paint.Style.STROKE
            }
            state.selectedPage.outlines.forEach { ol ->
                outlinePaint.strokeWidth = ol.strokeWidth * (renderWidth / 1000f).coerceAtLeast(1f)
                val p = PathParser.buildScaledPath(ol.pathCommands, wF, hF)
                canvas.drawPath(p, outlinePaint)
            }
        }

        val textPaint = android.graphics.Paint().apply {
            isAntiAlias = true
            textAlign = android.graphics.Paint.Align.CENTER
        }
        state.stickers.forEach { sticker ->
            textPaint.textSize = sticker.size * (renderWidth / 800f)
            canvas.drawText(
                sticker.symbol,
                sticker.x * (renderWidth / 800f),
                (sticker.y + sticker.size / 3f) * (renderHeight / 800f),
                textPaint
            )
        }

        ArtworkStorage.shareArtwork(context, bitmap, state.selectedPage.title)
    }
}
