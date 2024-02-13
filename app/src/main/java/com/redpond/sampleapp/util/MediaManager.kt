package com.redpond.sampleapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

class MediaManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val resolver = context.contentResolver

    fun decodeToBitmap(uri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val source = ImageDecoder.createSource(resolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(resolver, uri)
        }
    }

    fun createTempFile(fileName: String): File {
        return File.createTempFile(fileName, null, context.cacheDir)
    }

    fun writeBitmapToFile(file: File, bitmap: Bitmap) {
        file.outputStream().use { outputStream ->
            outputStream.write(ByteArrayOutputStream().use { byteArrayOutputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                byteArrayOutputStream.toByteArray()
            })
        }
    }
}