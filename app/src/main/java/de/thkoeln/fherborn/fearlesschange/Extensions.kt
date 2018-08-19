package de.thkoeln.fherborn.fearlesschange

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import de.thkoeln.fherborn.fearlesschange.ui.glide.GlideRequest


/**
 * Created by florianherborn on 08.08.18.
 */
fun getResourceId(context: Context, resName: String, resIdentifier: String): Int? {
    return try {
        val resId = context.resources.getIdentifier(resName, resIdentifier, context.packageName)
        if(resId <= 0) null else resId
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

}