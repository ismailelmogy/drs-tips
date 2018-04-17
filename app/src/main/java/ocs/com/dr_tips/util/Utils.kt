package ocs.com.dr_tips.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File


object Utils {
    fun fixMediaDir() {
        val sdcard = Environment.getExternalStorageDirectory()
        if (sdcard != null) {
            val mediaDir = File(sdcard, "DCIM/Camera")
            if (!mediaDir.exists()) {
                mediaDir.mkdirs()
            }
        }
    }

    fun decodeBitmapFromStr(string: String): Bitmap {
        val decodedString = Base64.decode(string, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    fun encodeToBase64String(profileBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        profileBitmap.compress(Bitmap.CompressFormat.JPEG, 30, bytes)
        return Base64.encodeToString(bytes.toByteArray(), Base64.NO_WRAP)
    }

}