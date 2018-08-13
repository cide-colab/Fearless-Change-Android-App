package de.thkoeln.fherborn.fearlesschange.ui.activities

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.ui.glide.GlideApp
import kotlinx.android.synthetic.main.activity_app.*
import kotlinx.android.synthetic.main.layout_default_app_bar.*

abstract class AppActivity : AppCompatActivity() {

    private var contentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_app)

        setupActionBar()
        onSetBackground()
    }

    private fun onSetBackground() {
        GlideApp.with(this)
                .load(R.drawable.app_bg).fitCenter().into(object: SimpleTarget<Drawable>(){
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        activity_wrapper.background = resource
                    }

                })
    }

    private fun setupActionBar() {
        setSupportActionBar(action_bar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setContentView(layoutResID: Int) {
        activity_content.removeAllViews()
        contentView = layoutInflater.inflate(layoutResID,activity_content, true)
    }

    override fun setContentView(view: View?) {
        activity_content.removeAllViews()
        activity_content.addView(view)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        setContentView(view)
    }

    fun getContentView() = contentView
}
