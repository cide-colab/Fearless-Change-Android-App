package de.thkoeln.fherborn.fearlesschange.v2.helper.extensions

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import de.thkoeln.fherborn.fearlesschange.ui.glide.GlideApp
import de.thkoeln.fherborn.fearlesschange.ui.glide.GlideRequest


fun GlideRequest<Drawable>.toBackgroundOf(view: View) = into(object : SimpleTarget<Drawable>() {
    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        view.background = resource
    }
})

fun ImageView.setOptimizedImage(drawable: Drawable?, defaultImageResource: Int? = null) {
    var glideRequest = GlideApp.with(context).load(drawable)
    defaultImageResource?.let { glideRequest = glideRequest.placeholder(it) }
    glideRequest.fitCenter().into(this)
}

fun ImageView.setOptimizedImage(resourceId: Int?, defaultImageResource: Int? = null) {
    var glideRequest = GlideApp.with(context).load(resourceId)
    defaultImageResource?.let { glideRequest = glideRequest.placeholder(it) }
    glideRequest.fitCenter().into(this)
}

fun ImageView.setOptimizedImage(name: String?, defaultImageResource: Int? = null) {
    name?.let {
        setOptimizedImage(getResourceId(context, it, "drawable"), defaultImageResource)
    }
}

fun View.setOptimizedBackground(resourceId: Int) {
    GlideApp.with(context).load(resourceId).fitCenter().toBackgroundOf(this)
}