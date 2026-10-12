package com.example.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import com.example.model.SavedArtwork
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

object ArtworkStorage {

    private const val ARTWORKS_DIR = "artworks"
    private const val SHARED_DIR = "shared_artworks"
    private const val METADATA_FILE = "artworks_meta.json"

    private fun getArtworksDirectory(context: Context): File {
        val dir = File(context.filesDir, ARTWORKS_DIR)
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    private fun getSharedDirectory(context: Context): File {
        val dir = File(context.cacheDir, SHARED_DIR)
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    fun saveArtworkBitmap(
        context: Context,
        id: String,
        pageId: String,
        title: String,
        bitmap: Bitmap,
        strokesCount: Int,
        filledCount: Int
    ): SavedArtwork? {
        return try {
            val dir = getArtworksDirectory(context)
            val fileName = "artwork_$id.png"
            val file = File(dir, fileName)

            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }

            val savedItem = SavedArtwork(
                id = id,
                pageId = pageId,
                title = title,
                timestamp = System.currentTimeMillis(),
                thumbnailFileName = fileName,
                strokesCount = strokesCount,
                filledCount = filledCount
            )

            // Update metadata json
            val list = loadAllSavedArtworks(context).toMutableList()
            // Replace if existing or prepend
            val existingIndex = list.indexOfFirst { it.id == id }
            if (existingIndex >= 0) {
                list[existingIndex] = savedItem
            } else {
                list.add(0, savedItem)
            }
            saveMetadataList(context, list)

            savedItem
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun loadAllSavedArtworks(context: Context): List<SavedArtwork> {
        val metaFile = File(getArtworksDirectory(context), METADATA_FILE)
        if (!metaFile.exists()) return emptyList()

        return try {
            val jsonStr = metaFile.readText()
            val array = JSONArray(jsonStr)
            val list = mutableListOf<SavedArtwork>()
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    SavedArtwork(
                        id = obj.getString("id"),
                        pageId = obj.optString("pageId", "page_royal_princess"),
                        title = obj.optString("title", "Princess Artwork"),
                        timestamp = obj.optLong("timestamp", System.currentTimeMillis()),
                        thumbnailFileName = obj.getString("thumbnailFileName"),
                        strokesCount = obj.optInt("strokesCount", 0),
                        filledCount = obj.optInt("filledCount", 0)
                    )
                )
            }
            list
        } catch (_: Exception) {
            emptyList()
        }
    }

    private fun saveMetadataList(context: Context, list: List<SavedArtwork>) {
        try {
            val metaFile = File(getArtworksDirectory(context), METADATA_FILE)
            val array = JSONArray()
            for (item in list) {
                val obj = JSONObject().apply {
                    put("id", item.id)
                    put("pageId", item.pageId)
                    put("title", item.title)
                    put("timestamp", item.timestamp)
                    put("thumbnailFileName", item.thumbnailFileName)
                    put("strokesCount", item.strokesCount)
                    put("filledCount", item.filledCount)
                }
                array.put(obj)
            }
            metaFile.writeText(array.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteArtwork(context: Context, id: String): Boolean {
        return try {
            val list = loadAllSavedArtworks(context).toMutableList()
            val item = list.firstOrNull { it.id == id }
            if (item != null) {
                val file = File(getArtworksDirectory(context), item.thumbnailFileName)
                if (file.exists()) file.delete()
                list.removeAll { it.id == id }
                saveMetadataList(context, list)
            }
            true
        } catch (_: Exception) {
            false
        }
    }

    fun shareArtwork(context: Context, bitmap: Bitmap, title: String) {
        try {
            val sharedDir = getSharedDirectory(context)
            val shareFile = File(sharedDir, "princess_artwork_${System.currentTimeMillis()}.png")
            FileOutputStream(shareFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                shareFile
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_SUBJECT, "My Princess Coloring Masterpiece: $title")
                putExtra(Intent.EXTRA_TEXT, "Look at the magical princess artwork I colored with Princess Coloring! ✨👑")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            val chooser = Intent.createChooser(shareIntent, "Share Princess Masterpiece")
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getArtworkFile(context: Context, fileName: String): File {
        return File(getArtworksDirectory(context), fileName)
    }
}
