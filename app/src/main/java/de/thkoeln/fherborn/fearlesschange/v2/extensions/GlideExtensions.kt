package de.thkoeln.fherborn.fearlesschange.v2.extensions

import android.databinding.BindingAdapter
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
    GlideApp.with(context).load(drawable).let {glide ->
        defaultImageResource?.let {
            glide.placeholder(it)
        }
        glide
    }.fitCenter().into(this)
}

fun ImageView.setOptimizedImage(resourceId: Int?, defaultImageResource: Int? = null) {
    GlideApp.with(context).load(resourceId).let {glide ->
        defaultImageResource?.let {
            glide.placeholder(it)
        }
        glide
    }.fitCenter().into(this)
}

fun ImageView.setOptimizedImage(name: String?, defaultImageResource: Int? = null) {
    name?.let {
        setOptimizedImage(getResourceId(context, name, "drawable"), defaultImageResource)
    }
}

@BindingAdapter("optimizedBackground")
fun View.setOptimizedBackground(drawable: Drawable) {
    GlideApp.with(context).load(drawable).fitCenter().toBackgroundOf(this)
}

fun View.setOptimizedBackground(resourceId: Int) {
    GlideApp.with(context).load(resourceId).fitCenter().toBackgroundOf(this)
}