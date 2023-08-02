package com.example.s2m.android.util

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.runtime.Composable

private fun convertComposableToBitmap(composable: @Composable () -> Unit): Bitmap {
    var resultBitmap: Bitmap? = null
    val screenshot = Screenshot.captureToImage {
        composable()
    }
    screenshot?.let { image ->
        resultBitmap = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(resultBitmap!!)
        image.draw(canvas)
    }
    return resultBitmap ?: throw IllegalArgumentException("Failed to convert Composable to Bitmap.")
}