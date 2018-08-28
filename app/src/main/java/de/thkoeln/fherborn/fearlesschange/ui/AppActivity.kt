package de.thkoeln.fherborn.fearlesschange.ui

import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.helper.SnackBarMessage
import de.thkoeln.fherborn.fearlesschange.ui.search.SearchActivity


abstract class AppActivity : AppCompatActivity() {

    private var contentView: View? = null

    override fun setContentView(layoutResID: Int) {
        setContentView(LayoutInflater.from(this).inflate(layoutResID, null))
    }

    override fun setContentView(view: View?) {
        contentView = view
        super.setContentView(view)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        contentView = view
        super.setContentView(view, params)
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

    protected fun showSnackBar(message: SnackBarMessage) {
        contentView?.let { Snackbar.make(it, message.message, message.duration).show() }
    }

    protected fun openPopup(popup: DialogFragment) {
        supportFragmentManager?.let { fm ->
            popup.show(fm, null)
        }
    }

}
