package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.PrincessPagesData
import com.example.model.ColorPalettes
import com.example.model.ToolType
import com.example.viewmodel.ColoringViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Princess Coloring", appName)
    }

    @Test
    fun `verify princess coloring pages and color categories`() {
        val pages = PrincessPagesData.allPages
        assertTrue(pages.size >= 6)

        val royalPage = pages.first()
        assertEquals("Royal Princess & Tiara", royalPage.title)
        assertTrue(royalPage.regions.isNotEmpty())
        assertTrue(royalPage.outlines.isNotEmpty())

        val categories = ColorPalettes.allCategories
        assertTrue(categories.isNotEmpty())
        assertTrue(categories.any { it.name == "Royal Princess" })
        assertTrue(categories.any { it.name == "Ice & Snow" })
        assertTrue(categories.any { it.name == "Mermaid Sea" })
    }

    @Test
    fun `verify coloring view model tools and state`() {
        val viewModel = ColoringViewModel()
        val initialState = viewModel.uiState.value

        assertEquals(ToolType.PEN, initialState.activeTool)
        assertNotNull(initialState.selectedPage)
        assertTrue(initialState.isSoundEnabled)

        // Select tool
        viewModel.selectTool(ToolType.SPRAY)
        assertEquals(ToolType.SPRAY, viewModel.uiState.value.activeTool)

        viewModel.selectTool(ToolType.SPARKLE)
        assertEquals(ToolType.SPARKLE, viewModel.uiState.value.activeTool)

        // Toggle sound
        viewModel.toggleSound()
        assertFalse(viewModel.uiState.value.isSoundEnabled)
    }
}
