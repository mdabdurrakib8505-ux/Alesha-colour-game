package com.example.data

import android.graphics.Path
import android.graphics.RectF
import androidx.compose.ui.geometry.Offset
import com.example.model.OutlinePathData
import com.example.model.RegionPathData

/**
 * Utility to parse compact normalized path command strings (0..1000 coordinate space)
 * into Android graphics Path objects scaled to actual canvas dimensions.
 *
 * Supported commands:
 * M x,y       - Move to
 * L x,y       - Line to
 * Q cx,cy,x,y - Quadratic bezier to
 * C cx1,cy1,cx2,cy2,x,y - Cubic bezier to
 * Z           - Close path
 */
object PathParser {

    fun buildScaledPath(commandString: String, width: Float, height: Float): Path {
        val path = Path()
        if (commandString.isBlank()) return path

        val scaleX = width / 1000f
        val scaleY = height / 1000f

        val tokens = commandString.trim().split("\\s+".toRegex())
        var i = 0

        while (i < tokens.size) {
            val cmd = tokens[i]
            when (cmd.uppercase()) {
                "M" -> {
                    if (i + 1 < tokens.size) {
                        val coords = tokens[++i].split(",")
                        if (coords.size >= 2) {
                            val x = coords[0].toFloatOrNull()?.times(scaleX) ?: 0f
                            val y = coords[1].toFloatOrNull()?.times(scaleY) ?: 0f
                            path.moveTo(x, y)
                        }
                    }
                }
                "L" -> {
                    if (i + 1 < tokens.size) {
                        val coords = tokens[++i].split(",")
                        if (coords.size >= 2) {
                            val x = coords[0].toFloatOrNull()?.times(scaleX) ?: 0f
                            val y = coords[1].toFloatOrNull()?.times(scaleY) ?: 0f
                            path.lineTo(x, y)
                        }
                    }
                }
                "Q" -> {
                    if (i + 1 < tokens.size) {
                        val coords = tokens[++i].split(",")
                        if (coords.size >= 4) {
                            val cx = coords[0].toFloatOrNull()?.times(scaleX) ?: 0f
                            val cy = coords[1].toFloatOrNull()?.times(scaleY) ?: 0f
                            val x = coords[2].toFloatOrNull()?.times(scaleX) ?: 0f
                            val y = coords[3].toFloatOrNull()?.times(scaleY) ?: 0f
                            path.quadTo(cx, cy, x, y)
                        }
                    }
                }
                "C" -> {
                    if (i + 1 < tokens.size) {
                        val coords = tokens[++i].split(",")
                        if (coords.size >= 6) {
                            val cx1 = coords[0].toFloatOrNull()?.times(scaleX) ?: 0f
                            val cy1 = coords[1].toFloatOrNull()?.times(scaleY) ?: 0f
                            val cx2 = coords[2].toFloatOrNull()?.times(scaleX) ?: 0f
                            val cy2 = coords[3].toFloatOrNull()?.times(scaleY) ?: 0f
                            val x = coords[4].toFloatOrNull()?.times(scaleX) ?: 0f
                            val y = coords[5].toFloatOrNull()?.times(scaleY) ?: 0f
                            path.cubicTo(cx1, cy1, cx2, cy2, x, y)
                        }
                    }
                }
                "Z" -> {
                    path.close()
                }
            }
            i++
        }
        return path
    }

    /**
     * Tests if a tap point (x, y) is inside the given closed path.
     */
    fun isPointInPath(path: Path, point: Offset): Boolean {
        val bounds = RectF()
        path.computeBounds(bounds, true)
        if (!bounds.contains(point.x, point.y)) {
            return false
        }

        val region = android.graphics.Region()
        val clip = android.graphics.Region(
            bounds.left.toInt(),
            bounds.top.toInt(),
            bounds.right.toInt().coerceAtLeast(bounds.left.toInt() + 1),
            bounds.bottom.toInt().coerceAtLeast(bounds.top.toInt() + 1)
        )
        region.setPath(path, clip)
        return region.contains(point.x.toInt(), point.y.toInt())
    }
}
