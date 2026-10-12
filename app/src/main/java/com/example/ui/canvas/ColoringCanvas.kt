package com.example.ui.canvas

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Path as AndroidPath
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke as ComposeStroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.data.PathParser
import com.example.model.CanvasSparkle
import com.example.model.PrincessPage
import com.example.model.SparkleDot
import com.example.model.SprayDot
import com.example.model.StickerStamp
import com.example.model.Stroke
import com.example.model.ToolType
import com.example.ui.theme.TiaraGold
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun ColoringCanvas(
    page: PrincessPage,
    strokes: List<Stroke>,
    filledRegions: Map<String, Long>,
    stickers: List<StickerStamp>,
    activeTool: ToolType,
    activeColor: Color,
    strokeWidth: Float,
    selectedSticker: String,
    zoomScale: Float,
    panOffset: Offset,
    onZoomChange: (Float, Offset) -> Unit,
    onAddStroke: (Stroke) -> Unit,
    onFillRegion: (String, Long) -> Unit,
    onAddSticker: (StickerStamp) -> Unit,
    onStrokeFinished: () -> Unit,
    onCanvasBitmapCaptured: ((Bitmap) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current

    // Current in-progress drawing points
    var currentPoints by remember { mutableStateOf<List<Offset>>(emptyList()) }
    var currentSprayDots by remember { mutableStateOf<List<SprayDot>>(emptyList()) }
    var currentSparkles by remember { mutableStateOf<List<SparkleDot>>(emptyList()) }

    // Animated sparkle bursts on canvas
    val activeSparkles = remember { mutableStateListOf<CanvasSparkle>() }

    fun spawnSparkles(center: Offset, count: Int = 8, color: Color = TiaraGold) {
        val sparkles = (0 until count).map {
            val angle = Random.nextFloat() * 2f * Math.PI.toFloat()
            val dist = Random.nextFloat() * 40f + 10f
            CanvasSparkle(
                x = center.x + cos(angle) * dist,
                y = center.y + sin(angle) * dist,
                size = Random.nextFloat() * 14f + 6f,
                color = if (Random.nextBoolean()) color else Color(0xFFFF80AB)
            )
        }
        activeSparkles.addAll(sparkles)
        coroutineScope.launch {
            kotlinx.coroutines.delay(600)
            activeSparkles.removeAll(sparkles)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            // 2-finger zoom and pan
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    val newScale = (zoomScale * zoom).coerceIn(1.0f, 4.0f)
                    val newPan = if (newScale <= 1.0f) Offset.Zero else panOffset + pan
                    onZoomChange(newScale, newPan)
                }
            }
            // Tap gestures for bucket fill & stickers
            .pointerInput(activeTool, page, selectedSticker, activeColor, zoomScale, panOffset) {
                detectTapGestures { rawOffset ->
                    val canvasOffset = (rawOffset - panOffset) / zoomScale

                    if (activeTool == ToolType.FILL_BUCKET) {
                        // Test each region path
                        val width = size.width.toFloat()
                        val height = size.height.toFloat()
                        for (region in page.regions.reversed()) {
                            val scaledPath = PathParser.buildScaledPath(region.pathCommands, width, height)
                            if (PathParser.isPointInPath(scaledPath, canvasOffset)) {
                                onFillRegion(region.id, activeColor.toArgb().toLong())
                                spawnSparkles(rawOffset, 12, activeColor)
                                break
                            }
                        }
                    } else if (activeTool == ToolType.STICKER) {
                        val stamp = StickerStamp(
                            symbol = selectedSticker,
                            x = canvasOffset.x,
                            y = canvasOffset.y,
                            size = 56f
                        )
                        onAddSticker(stamp)
                        spawnSparkles(rawOffset, 10, TiaraGold)
                    }
                }
            }
            // Drag gestures for free-hand brushes (Pen, Watercolor, Spray, Sparkle, Neon, Eraser)
            .pointerInput(activeTool, activeColor, strokeWidth, zoomScale, panOffset) {
                if (activeTool == ToolType.FILL_BUCKET || activeTool == ToolType.STICKER) return@pointerInput

                detectDragGestures(
                    onDragStart = { rawStart ->
                        val start = (rawStart - panOffset) / zoomScale
                        currentPoints = listOf(start)
                        currentSprayDots = emptyList()
                        currentSparkles = emptyList()

                        if (activeTool == ToolType.SPRAY) {
                            currentSprayDots = generateSprayDots(start, strokeWidth)
                        } else if (activeTool == ToolType.SPARKLE) {
                            currentSparkles = listOf(generateSparkleDot(start, activeColor))
                        }
                    },
                    onDrag = { change, _ ->
                        change.consume()
                        val rawPos = change.position
                        val pos = (rawPos - panOffset) / zoomScale
                        val lastPoint = currentPoints.lastOrNull() ?: pos
                        currentPoints = currentPoints + pos

                        if (activeTool == ToolType.SPRAY) {
                            val newDots = generateSprayDots(pos, strokeWidth)
                            currentSprayDots = currentSprayDots + newDots
                        } else if (activeTool == ToolType.SPARKLE) {
                            val dist = hypot(pos.x - lastPoint.x, pos.y - lastPoint.y)
                            if (dist > 20f) {
                                currentSparkles = currentSparkles + generateSparkleDot(pos, activeColor)
                            }
                        }
                    },
                    onDragEnd = {
                        if (currentPoints.isNotEmpty()) {
                            val stroke = Stroke(
                                id = UUID.randomUUID().toString(),
                                tool = activeTool,
                                colorArgb = if (activeTool == ToolType.ERASER) Color.White.toArgb().toLong() else activeColor.toArgb().toLong(),
                                strokeWidth = strokeWidth,
                                alpha = when (activeTool) {
                                    ToolType.WATERCOLOR -> 0.38f
                                    ToolType.NEON -> 1.0f
                                    else -> 1.0f
                                },
                                points = currentPoints,
                                sprayDots = currentSprayDots,
                                sparkles = currentSparkles
                            )
                            onAddStroke(stroke)
                            val endPoint = currentPoints.last()
                            val screenEnd = endPoint * zoomScale + panOffset
                            spawnSparkles(screenEnd, 6, activeColor)
                            onStrokeFinished()
                        }
                        currentPoints = emptyList()
                        currentSprayDots = emptyList()
                        currentSparkles = emptyList()
                    },
                    onDragCancel = {
                        currentPoints = emptyList()
                        currentSprayDots = emptyList()
                        currentSparkles = emptyList()
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Apply zoom and pan transformations
            drawIntoCanvas { canvas ->
                canvas.save()
                canvas.translate(panOffset.x, panOffset.y)
                canvas.scale(zoomScale, zoomScale)

                // 1. Draw Page Filled Regions (Base Layer)
                if (!page.isBlankCanvas) {
                    page.regions.forEach { region ->
                        val fillColorLong = filledRegions[region.id] ?: region.defaultColorArgb
                        if (fillColorLong != null) {
                            val androidPath = PathParser.buildScaledPath(region.pathCommands, canvasWidth, canvasHeight)
                            val composePath = androidPath.asComposePath()
                            drawPath(
                                path = composePath,
                                color = Color(fillColorLong)
                            )
                        }
                    }
                } else {
                    // Blank canvas background
                    drawRect(color = Color.White)
                }

                // 2. Draw Committed Brush Strokes
                strokes.forEach { stroke ->
                    drawSingleStroke(stroke)
                }

                // 3. Draw In-Progress Active Stroke
                if (currentPoints.isNotEmpty()) {
                    val activeInProgressStroke = Stroke(
                        tool = activeTool,
                        colorArgb = if (activeTool == ToolType.ERASER) Color.White.toArgb().toLong() else activeColor.toArgb().toLong(),
                        strokeWidth = strokeWidth,
                        alpha = if (activeTool == ToolType.WATERCOLOR) 0.38f else 1.0f,
                        points = currentPoints,
                        sprayDots = currentSprayDots,
                        sparkles = currentSparkles
                    )
                    drawSingleStroke(activeInProgressStroke)
                }

                // 4. Draw Line Art Outlines on Top (if not blank canvas)
                if (!page.isBlankCanvas) {
                    page.outlines.forEach { outline ->
                        val androidPath = PathParser.buildScaledPath(outline.pathCommands, canvasWidth, canvasHeight)
                        val composePath = androidPath.asComposePath()
                        drawPath(
                            path = composePath,
                            color = Color(0xFF281C30), // Crisp deep royal purple outline
                            style = ComposeStroke(
                                width = outline.strokeWidth * (canvasWidth / 1000f).coerceAtLeast(0.8f),
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }
                }

                // 5. Draw Stickers Stamps
                stickers.forEach { sticker ->
                    val paint = Paint().apply {
                        textSize = sticker.size
                        textAlign = Paint.Align.CENTER
                        isAntiAlias = true
                    }
                    canvas.nativeCanvas.drawText(
                        sticker.symbol,
                        sticker.x,
                        sticker.y + (sticker.size / 3f),
                        paint
                    )
                }

                canvas.restore()
            }

            // 6. Draw Animated Sparkle Bursts in Screen Coordinates
            activeSparkles.forEach { sparkle ->
                drawSparkleStar(
                    center = Offset(sparkle.x, sparkle.y),
                    size = sparkle.size,
                    color = sparkle.color
                )
            }
        }
    }
}

private fun DrawScope.drawSingleStroke(stroke: Stroke) {
    if (stroke.points.isEmpty()) return

    when (stroke.tool) {
        ToolType.PEN -> {
            drawSmoothedPath(
                points = stroke.points,
                color = stroke.color,
                strokeWidth = stroke.strokeWidth,
                alpha = stroke.alpha
            )
        }
        ToolType.WATERCOLOR -> {
            drawSmoothedPath(
                points = stroke.points,
                color = stroke.color,
                strokeWidth = stroke.strokeWidth * 1.3f,
                alpha = 0.38f
            )
        }
        ToolType.SPRAY -> {
            // Draw spray dots
            stroke.sprayDots.forEach { dot ->
                drawCircle(
                    color = stroke.color.copy(alpha = dot.alpha),
                    radius = dot.radius,
                    center = Offset(dot.x, dot.y)
                )
            }
        }
        ToolType.SPARKLE -> {
            // Draw a subtle connecting glow path
            drawSmoothedPath(
                points = stroke.points,
                color = stroke.color.copy(alpha = 0.35f),
                strokeWidth = stroke.strokeWidth * 0.7f,
                alpha = 0.35f
            )
            // Draw glittering stars
            stroke.sparkles.forEach { sparkle ->
                drawSparkleStar(
                    center = Offset(sparkle.x, sparkle.y),
                    size = sparkle.size,
                    color = Color(sparkle.colorArgb)
                )
            }
        }
        ToolType.NEON -> {
            // Layer 1: Wide outer glow
            drawSmoothedPath(
                points = stroke.points,
                color = stroke.color.copy(alpha = 0.22f),
                strokeWidth = stroke.strokeWidth * 2.8f,
                alpha = 0.22f
            )
            // Layer 2: Medium inner glow
            drawSmoothedPath(
                points = stroke.points,
                color = stroke.color.copy(alpha = 0.55f),
                strokeWidth = stroke.strokeWidth * 1.6f,
                alpha = 0.55f
            )
            // Layer 3: Bright white-hot central core
            drawSmoothedPath(
                points = stroke.points,
                color = Color.White,
                strokeWidth = stroke.strokeWidth * 0.55f,
                alpha = 0.95f
            )
        }
        ToolType.ERASER -> {
            drawSmoothedPath(
                points = stroke.points,
                color = Color.White,
                strokeWidth = stroke.strokeWidth * 1.5f,
                alpha = 1.0f
            )
        }
        else -> {}
    }
}

/**
 * Creates smooth quadratic bezier curves connecting points.
 */
private fun DrawScope.drawSmoothedPath(
    points: List<Offset>,
    color: Color,
    strokeWidth: Float,
    alpha: Float
) {
    if (points.isEmpty()) return
    if (points.size == 1) {
        drawCircle(
            color = color.copy(alpha = alpha),
            radius = strokeWidth / 2f,
            center = points[0]
        )
        return
    }

    val path = Path()
    path.moveTo(points[0].x, points[0].y)

    for (i in 1 until points.size) {
        val prev = points[i - 1]
        val curr = points[i]
        val midX = (prev.x + curr.x) / 2f
        val midY = (prev.y + curr.y) / 2f
        path.quadraticTo(prev.x, prev.y, midX, midY)
    }
    path.lineTo(points.last().x, points.last().y)

    drawPath(
        path = path,
        color = color.copy(alpha = alpha),
        style = ComposeStroke(
            width = strokeWidth,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}

/**
 * Draws a sparkling 4-point diamond star with glowing center.
 */
private fun DrawScope.drawSparkleStar(
    center: Offset,
    size: Float,
    color: Color
) {
    // Soft glowing halo
    drawCircle(
        color = color.copy(alpha = 0.3f),
        radius = size * 0.8f,
        center = center
    )

    // 4-point star path
    val path = Path()
    val rOut = size
    val rIn = size * 0.22f

    for (i in 0 until 8) {
        val r = if (i % 2 == 0) rOut else rIn
        val angle = i * Math.PI / 4.0 - (Math.PI / 2.0)
        val x = center.x + (r * cos(angle)).toFloat()
        val y = center.y + (r * sin(angle)).toFloat()
        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()

    drawPath(path = path, color = color)
    // Bright white core
    drawCircle(
        color = Color.White.copy(alpha = 0.9f),
        radius = size * 0.25f,
        center = center
    )
}

/**
 * Generates dispersed spray paint dots for authentic airbrush stippling.
 */
private fun generateSprayDots(center: Offset, brushWidth: Float): List<SprayDot> {
    val dotCount = (brushWidth * 0.8f).toInt().coerceIn(6, 28)
    val maxRadius = brushWidth * 0.9f

    return (0 until dotCount).map {
        // Gaussian distribution concentrated towards center
        val angle = Random.nextFloat() * 2f * Math.PI.toFloat()
        val dist = (Random.nextFloat() * Random.nextFloat()) * maxRadius
        val dotRadius = Random.nextFloat() * (brushWidth * 0.12f).coerceAtLeast(1.5f) + 1.2f
        val alpha = Random.nextFloat() * 0.5f + 0.35f

        SprayDot(
            x = center.x + cos(angle) * dist,
            y = center.y + sin(angle) * dist,
            radius = dotRadius,
            alpha = alpha
        )
    }
}

private fun generateSparkleDot(center: Offset, baseColor: Color): SparkleDot {
    val colors = listOf(
        TiaraGold.toArgb().toLong(),
        baseColor.toArgb().toLong(),
        Color(0xFFFF80AB).toArgb().toLong(),
        Color(0xFFE040FB).toArgb().toLong(),
        Color(0xFF00E5FF).toArgb().toLong()
    )
    return SparkleDot(
        x = center.x + (Random.nextFloat() * 16f - 8f),
        y = center.y + (Random.nextFloat() * 16f - 8f),
        size = Random.nextFloat() * 16f + 8f,
        rotation = Random.nextFloat() * 360f,
        colorArgb = colors.random()
    )
}
