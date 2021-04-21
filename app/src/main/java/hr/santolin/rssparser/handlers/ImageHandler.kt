package hr.santolin.rssparser.handlers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import com.google.android.material.tabs.TabLayout
import hr.santolin.rssparser.factory.createGetHttpUrlConnection
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

private const val PICTURE_HEIGHT = 750
private const val PICTURE_WIDTH = 750
private const val QUALITY = 100
private const val TAG = "ImageHandler"

fun downloadImageAndStore(context: Context, url: String, fileName: String): String {
    val ext = url.substring(url.lastIndexOf("."))//.jpg
    val file = getFile(context, fileName, ext)
    try {
// open stream for url
        val con = createGetHttpUrlConnection(url)
        con.inputStream.use { inputStream ->
            FileOutputStream(file).use { fos ->
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val resizedBitmap: Bitmap = getResizedBitmap(bitmap, PICTURE_WIDTH, PICTURE_HEIGHT)
                val buffer: ByteArray = getBytesFromBitmap(resizedBitmap)
                fos.write(buffer)
                return file.absolutePath
            }

        }
    } catch (e: Exception) {
        Log.e(TAG, e.message, e)
    }
    return file.name
}

fun getBytesFromBitmap(resizedBitmap: Bitmap): ByteArray {
    var baos: ByteArrayOutputStream = ByteArrayOutputStream()
    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, baos)
    return baos.toByteArray()
}

fun getResizedBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
    val width = bitmap.width
    val height = bitmap.height
    val scaleWidth = newWidth.toFloat() / width
    val scaleHeight = newHeight.toFloat() / height
    val matrix = Matrix()
    matrix.postScale(scaleWidth, scaleHeight)
    return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false)
}

fun getFile(context: Context, fileName: String, ext: String): File {
    //kreiraj direktorij
    val dir: File? = context.applicationContext.getExternalFilesDir(null)
    var file = File(dir, File.separator.toString() + fileName + ext)
    if (file.exists()) {
        file.delete()
    }
    return file
}
