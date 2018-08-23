package de.thkoeln.fherborn.fearlesschange.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.setOptimizedBackground
import kotlinx.android.synthetic.main.activity_app.*
import kotlinx.android.synthetic.main.app_bar.*

abstract class AppActivity : AppCompatActivity() {

    private var contentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_app)
        setupActionBar()
        onSetBackground()
    }

    private fun onSetBackground() {
        activity_wrapper.setOptimizedBackground(R.drawable.app_bg)
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
}
