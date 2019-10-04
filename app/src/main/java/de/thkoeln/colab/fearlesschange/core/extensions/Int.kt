package de.thkoeln.colab.fearlesschange.core.extensions

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue


fun Int.toDp() = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.toPx() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.dp() = toFloat().dp()
fun Float.dp() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

fun Int.toPx(context: Context) = convertDpToPixel(this.toFloat(), context)
fun Int.toDp(context: Context) = convertPixelsToDp(this.toFloat(), context)


/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun convertDpToPixel(dp: Float, context: Context): Float {
    return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param px A value in px (pixels) unit. Which we need to convert into db
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
fun convertPixelsToDp(px: Float, context: Context): Float {
    return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}