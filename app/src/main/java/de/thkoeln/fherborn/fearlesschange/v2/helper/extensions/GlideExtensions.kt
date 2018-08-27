package de.thkoeln.fherborn.fearlesschange.v2.helper.extensions

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import de.thkoeln.fherborn.fearlesschange.v2.helper.glide.GlideApp
import de.thkoeln.fherborn.fearlesschange.v2.helper.glide.GlideRequest


fun GlideRequest<Drawable>.toBackgroundOf(view: View) = into(object : SimpleTarget<Drawable>() {
    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        view.background = resource
    }
})

fun ImageView.setOptimizedImage(resourceId: Int?, defaultImageResource: Int? = null) {
    var glideRequest = GlideApp.with(context.applicationContext).load(resourceId)
    defaultImageResource?.let { glideRequest = glideRequest.placeholder(it) }
    glideRequest.fitCenter().into(this)
}

fun View.setOptimizedBackground(resourceId: Int) {
    GlideApp.with(context.applicationContext).load(resourceId).fitCenter().toBackgroundOf(this)
}