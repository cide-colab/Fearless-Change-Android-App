package de.thkoeln.fherborn.fearlesschange.v2.helper.extensions

import android.content.Context


/**
 * Created by florianherborn on 08.08.18.
 */
fun getResourceId(context: Context, resName: String, resIdentifier: String): Int? {
    return try {
        val resId = context.resources.getIdentifier(resName, resIdentifier, context.packageName)
        if (resId <= 0) null else resId
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

}
