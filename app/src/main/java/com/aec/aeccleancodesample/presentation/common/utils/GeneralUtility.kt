package com.aec.aeccleancodesample.presentation.common.utils

import android.app.Activity
import android.app.Dialog
import android.content.*
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.aec.aeccleancodesample.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.platform.MaterialSharedAxis
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class GeneralUtility {

    companion object {
        private const val TAG = "GeneralUtility"

        fun buildHorizontalTransition(forward: Boolean) =
            MaterialSharedAxis(MaterialSharedAxis.X, forward).apply {
                duration = 500
            }

        fun returnTwoDigitNumber(num: Int): String {

            return when {
                num in 0..9 -> "0$num"
                num > 99 -> num.toString() + ""
                else -> "" + num
            }
        }

        fun makeCall(context: Context, number: String) {

            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            callIntent.data = Uri.parse("tel:$number")
            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(callIntent)
        }

        fun sendWhatsappMessage(context: Context, url: String) {

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            context.startActivity(intent)
        }

        fun prepareBottomSheetDialog(
            dialogInterface: DialogInterface,
            dialogHeight: Int,
            peekHeight: Int
        ) {

            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let {
                val behaviour = BottomSheetBehavior.from(it)
                setBottomSheetHeight(it, dialogHeight)
                behaviour.peekHeight =
                    Resources.getSystem().displayMetrics.heightPixels * peekHeight / 100
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        fun setBottomSheetHeight(bottomSheet: View, percentage: Int) {

            val height = Resources.getSystem().displayMetrics.heightPixels * percentage / 100

            val layoutParams = bottomSheet.layoutParams
            layoutParams.height = height
            bottomSheet.layoutParams = layoutParams
        }

        fun checkAppInstalled(context: Context, packageName: String): Boolean {

            return try {
                context.packageManager.getApplicationInfo(packageName, 0).enabled
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
        }

        fun getCurrentYear(): Int =
            Calendar.getInstance().get(Calendar.YEAR)

        fun getCurrentMonth(): Int =
            Calendar.getInstance().get(Calendar.MONTH) + 1

        fun getCurrentDay(): Int =
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        fun getProgressDialog(context: Context): Dialog {

            val mDialog = Dialog(context)
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mDialog.setCancelable(false)
            mDialog.setContentView(R.layout.layout_dialog_loading_indicator)

            mDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

            return mDialog
        }

        @Suppress("DEPRECATION")
        private fun getDialogLayoutParams(
            context: Activity,
            isFullWidth: Boolean
        ): WindowManager.LayoutParams {
            val size = Point()
            (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                context.display
            } else {
                context.windowManager.defaultDisplay
            })?.getSize(size)
            val width: Int = size.x

            val params = WindowManager.LayoutParams()
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            if (isFullWidth)
                params.width = width - 100
            else
                params.width = width * 3 / 4
            params.dimAmount = 0.7f

            return params
        }

        @Throws(IOException::class)
        fun readStream(stream: InputStream): String {

            val br = BufferedReader(InputStreamReader(stream))
            val buffer = CharArray(1024)
            val sb = StringBuilder()
            var readCount: Int
            while (br.read(buffer).also { readCount = it } != -1) {
                sb.append(buffer, 0, readCount)
            }
            return sb.toString()
        }

        fun copyText(context: Context, text: String) {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Bill Number", text)
            clipboard.setPrimaryClip(clip)
        }

        fun readFileFromAssets(context: Context, filePath: String): String {

            val byteArrayOutputStream = ByteArrayOutputStream()
            val assetManager = context.assets

            try {
                val inputStream = assetManager.open(filePath)
                var i: Int = inputStream.read()
                while (i != -1) {
                    byteArrayOutputStream.write(i)
                    i = inputStream.read()
                }
                inputStream.close()
            } catch (ex: IOException) {
                context.resources.getString(R.string.msg_can_not_get_data)
            }

            return byteArrayOutputStream.toString()
        }

        fun openUrlExternally(context: Context, url: String) {
            val i = Intent(Intent.ACTION_VIEW)
            i.addFlags(FLAG_ACTIVITY_NEW_TASK)
            i.data = Uri.parse(url)
            i.addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }

        fun shareLink(context: Context, link: String, subject: String) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, link)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        //region permissions
        fun checkPermissionIsGranted(context: Context, permission: String): Boolean {
            return ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }//endregion

        fun openMailApp(context: Context, mail: String, subject: String) {
            val mailIntent = Intent(Intent.ACTION_VIEW)
            val data =
                Uri.parse("mailto:?subject=$subject &to=$mail")
            mailIntent.data = data
            mailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(mailIntent)
        }

        fun calculateDaysBetweenTwoDates(startDate: String, endDate: String): Int {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            try {
                val start: Date = sdf.parse(startDate)!!
                val end: Date = sdf.parse(endDate)!!

                val diff: Long = end.time - start.time
                return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return 0
        }
    }

}