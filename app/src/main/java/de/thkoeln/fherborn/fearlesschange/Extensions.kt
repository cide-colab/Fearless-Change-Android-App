package de.thkoeln.fherborn.fearlesschange

import android.content.Context
import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.TextView
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

fun GlideRequest<Drawable>.toBackgroundOf(view: View) = into(object : SimpleTarget<Drawable>() {
    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        view.background = resource
    }
})

@BindingAdapter("optimizedBackground")
fun View.setOptimizedBackground(drawable: Drawable) {
    GlideApp.with(context).load(drawable).fitCenter().toBackgroundOf(this)
}