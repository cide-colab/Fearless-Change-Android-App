package de.thkoeln.fherborn.fearlesschange.v2

import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.extensions.setOptimizedBackground


abstract class AppActivity : AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        setContentView(LayoutInflater.from(this).inflate(layoutResID, null))
    }

    override fun setContentView(view: View?) {
        super.setContentView(prepareContentView(view))
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(prepareContentView(view), params)
    }

    private fun prepareContentView(view: View?) =
            view?.apply {
                setOptimizedBackground(R.drawable.app_bg)
            }

}
