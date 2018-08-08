package de.thkoeln.fherborn.fearlesschange

import android.content.Context


/**
 * Created by florianherborn on 08.08.18.
 */
fun <T>getResId(resName: String, c: Class<T>): Int {

    return try {
        val idField = c.getDeclaredField(resName)
        idField.getInt(idField)
    } catch (e: Exception) {
        e.printStackTrace()
        -1
    }

}

fun getResourceId(context: Context, resName: String, resIdentifier: String, packageName: String): Int? {
    return try {
        val resId = context.resources.getIdentifier(resName, resIdentifier, packageName)
        if(resId <= 0) null else resId
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

}