package de.thkoeln.fherborn.fearlesschange.v2.helper.view

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.setOptimizedImage
import kotlin.reflect.KProperty

/**
 * Created by Florian on 23.08.2018.
 */
open class ViewDelegation<T>(private val default: T, private val setToView: (T) -> Unit) {

    private var value: T = default

    operator fun getValue(thisRef: View?, property: KProperty<*>): T? {
        return value
    }
    operator fun setValue(thisRef: View?, property: KProperty<*>, value: T?) {
        this.value = value?:default
        setToView.invoke(this.value)
        thisRef?.invalidate()
        thisRef?.requestLayout()
    }
}

class ViewBindings(private val view: View, private val textDefault: String = "") {
    fun textFor(textViewId: Int, default: String = textDefault) = ViewDelegation(default) { view.findViewById<TextView>(textViewId).text = it }
    fun imageFor(imageViewId: Int, default: Int? = null) = ViewDelegation(default) { view.findViewById<ImageView>(imageViewId).setOptimizedImage(it, default) }
    fun booleanImageSwitchFor(imageViewId: Int, positiveImage: Int, negativeImage: Int, default: Boolean = false) = ViewDelegation(default) {
        view.findViewById<ImageView>(imageViewId).setImageResource(when (it) {
            true -> positiveImage
            else -> negativeImage
        })
    }
    fun <T> textVisibilitySwitchFor(textViewId: Int, vararg switchViewsIds: Int = intArrayOf(), default: T? = null, visibleIf: (T?) -> Boolean = { it?.toString()?.isNotEmpty()?:false }) = ViewDelegation(default) {

        val visibility = when { visibleIf(it) -> VISIBLE else -> INVISIBLE }
        val textView = view.findViewById<TextView>(textViewId).apply {
            this.text = it.toString()
            this.visibility = visibility
        }
        val switchViews = switchViewsIds.forEach { id -> view.findViewById<View>(id).visibility = visibility }
    }
    fun onClickListenerFor(viewId: Int, default: () -> Unit = {}) =
            ViewDelegation(default) {
                listener -> view.findViewById<View>(viewId).setOnClickListener {  listener.invoke() }
            }
}