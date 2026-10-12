package com.example.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

data class ColorCategory(
    val name: String,
    val iconEmoji: String,
    val colors: List<Color>
)

object ColorPalettes {

    val royalPrincess = ColorCategory(
        name = "Royal Princess",
        iconEmoji = "👑",
        colors = listOf(
            Color(0xFFFFD700), // Tiara Gold
            Color(0xFFFFE082), // Soft Gold
            Color(0xFFFF69B4), // Princess Pink
            Color(0xFFFF1493), // Deep Pink
            Color(0xFFE91E63), // Royal Rose
            Color(0xFFC2185B), // Ruby Magenta
            Color(0xFFFFB6C1), // Light Pink
            Color(0xFFF8BBD0), // Cotton Candy
            Color(0xFFCE93D8), // Soft Lilac
            Color(0xFF9C27B0), // Regal Purple
            Color(0xFF7B1FA2), // Deep Violet
            Color(0xFFFFFFFF)  // Pearl White
        )
    )

    val fairyTale = ColorCategory(
        name = "Fairy Magic",
        iconEmoji = "✨",
        colors = listOf(
            Color(0xFFBA68C8), // Magic Purple
            Color(0xFF8E24AA), // Fairy Orchid
            Color(0xFFE1BEE7), // Soft Lavender
            Color(0xFF80DEEA), // Fairy Cyan
            Color(0xFF4DD0E1), // Bright Cyan
            Color(0xFFA7FFEB), // Mint Sparkle
            Color(0xFFB9F6CA), // Fairy Green
            Color(0xFFFFF59D), // Starlight Yellow
            Color(0xFFFFD54F), // Amber Glow
            Color(0xFFFFAB91), // Sunset Peach
            Color(0xFFFF8A80), // Coral Blush
            Color(0xFFB39DDB)  // Dream Blue
        )
    )

    val icePrincess = ColorCategory(
        name = "Ice & Snow",
        iconEmoji = "❄️",
        colors = listOf(
            Color(0xFFFFFFFF), // Pure Snow
            Color(0xFFE0F7FA), // Diamond Ice
            Color(0xFFB2EBF2), // Frost Cyan
            Color(0xFF80DEEA), // Ice Crystal
            Color(0xFF4DD0E1), // Glacier Aqua
            Color(0xFF29B6F6), // Frost Azure
            Color(0xFF0288D1), // Deep Ice Blue
            Color(0xFF01579B), // Midnight Glacier
            Color(0xFFE1BEE7), // Ice Violet
            Color(0xFF90CAF9), // Soft Sky
            Color(0xFF3F51B5), // Royal Indigo
            Color(0xFFECEFF1)  // Silver Mist
        )
    )

    val mermaidLagoon = ColorCategory(
        name = "Mermaid Sea",
        iconEmoji = "🧜‍♀️",
        colors = listOf(
            Color(0xFF00B4D8), // Ocean Blue
            Color(0xFF0077B6), // Deep Ocean
            Color(0xFF48CAE4), // Aquamarine
            Color(0xFF90E0EF), // Sea Spray
            Color(0xFF80CBC4), // Seafoam
            Color(0xFF00897B), // Coral Teal
            Color(0xFF004D40), // Seaweed Dark
            Color(0xFFFF7043), // Coral Reef
            Color(0xFFFF8A65), // Starfish Peach
            Color(0xFFFFD54F), // Sand Gold
            Color(0xFFFFE082), // Pearl Shell
            Color(0xFFF48FB1)  // Sea Anemone
        )
    )

    val enchantedGarden = ColorCategory(
        name = "Garden Blossom",
        iconEmoji = "🌸",
        colors = listOf(
            Color(0xFF4CAF50), // Meadow Green
            Color(0xFF81C784), // Soft Leaf
            Color(0xFF388E3C), // Forest Green
            Color(0xFF1B5E20), // Deep Forest
            Color(0xFFC5E1A5), // Spring Bud
            Color(0xFFFFB300), // Sunflower
            Color(0xFFFF7043), // Tiger Lily
            Color(0xFFF06292), // Rose Blossom
            Color(0xFFE91E63), // Royal Peony
            Color(0xFF8D6E63), // Woodland Bark
            Color(0xFF5D4037), // Rich Earth
            Color(0xFFFFF176)  // Morning Dew
        )
    )

    val gemsAndGlitter = ColorCategory(
        name = "Gems & Tiara",
        iconEmoji = "💎",
        colors = listOf(
            Color(0xFFD81B60), // Ruby Red
            Color(0xFF1E88E5), // Sapphire Blue
            Color(0xFF00897B), // Emerald Green
            Color(0xFF8E24AA), // Amethyst Violet
            Color(0xFFFFB300), // Imperial Topaz
            Color(0xFFE91E63), // Pink Tourmaline
            Color(0xFF00ACC1), // Zircon Blue
            Color(0xFFFF6D00), // Mandarin Garnet
            Color(0xFFF48FB1), // Rose Quartz
            Color(0xFFEEEEEE), // Diamond White
            Color(0xFFFFD700), // Pure Gold
            Color(0xFF212121)  // Black Onyx
        )
    )

    val skinAndHair = ColorCategory(
        name = "Skin & Hair",
        iconEmoji = "👸",
        colors = listOf(
            Color(0xFFFFF0E1), // Fair Porcelain
            Color(0xFFFFE4C4), // Peach Warm
            Color(0xFFFFD1A9), // Light Honey
            Color(0xFFE8B98A), // Golden Tan
            Color(0xFFC68642), // Caramel Bronze
            Color(0xFF8D5524), // Rich Chestnut
            Color(0xFF5A3825), // Deep Cocoa
            Color(0xFF3B2219), // Midnight Espresso
            Color(0xFFFFE57F), // Platinum Blonde
            Color(0xFFFFB74D), // Golden Blonde
            Color(0xFFE65100), // Auburn Red
            Color(0xFF1A1A1A)  // Raven Black
        )
    )

    val allCategories = listOf(
        royalPrincess,
        fairyTale,
        icePrincess,
        mermaidLagoon,
        enchantedGarden,
        gemsAndGlitter,
        skinAndHair
    )

    val defaultSelectedColor = Color(0xFFFF4081) // Princess Pink
}
