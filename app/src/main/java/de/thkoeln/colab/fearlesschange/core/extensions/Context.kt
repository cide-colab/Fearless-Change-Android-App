package de.thkoeln.colab.fearlesschange.core.extensions

import android.content.Context

fun Context.getResourceId(resName: String?, resIdentifier: String) =
        resName?.let {
            try {
                val resId = resources.getIdentifier(resName, resIdentifier, packageName)
                if (resId <= 0) null else resId
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

fun Context.getDrawableId(name: String): Int? =
        getResourceId(name, "drawable")

