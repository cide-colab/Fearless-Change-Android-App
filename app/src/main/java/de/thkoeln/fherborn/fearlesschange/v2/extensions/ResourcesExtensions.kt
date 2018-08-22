package de.thkoeln.fherborn.fearlesschange.v2.extensions

import android.content.Context
import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import de.thkoeln.fherborn.fearlesschange.ui.glide.GlideApp
import de.thkoeln.fherborn.fearlesschange.ui.glide.GlideRequest


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
