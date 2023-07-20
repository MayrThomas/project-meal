package at.thomas.mayr.projectMeal.util

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import at.thomas.mayr.projectMeal.R
import java.io.ByteArrayOutputStream

object Utils {
    fun bitmapToBase64(resources: Resources): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val image = BitmapFactory.decodeResource(resources, R.drawable.placeholder_food)
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun base64ToBitmap(imageString: String): ImageBitmap {
        val imageBytes = Base64.decode(imageString, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        return bitmap.asImageBitmap()
    }
}