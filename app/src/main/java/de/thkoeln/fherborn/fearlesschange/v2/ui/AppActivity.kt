package de.thkoeln.fherborn.fearlesschange.v2.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.*
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.ui.activities.SearchActivity
import de.thkoeln.fherborn.fearlesschange.v2.helper.extensions.setOptimizedBackground


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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            startActivity(Intent(this, SearchActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
