package de.thkoeln.colab.fearlesschange.ui

import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.helper.SnackBarMessage


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

    protected fun showSnackBar(message: SnackBarMessage) {
        contentView?.let { Snackbar.make(it, message.message, message.duration).show() }
    }

    protected fun openPopup(popup: DialogFragment) {
        supportFragmentManager?.let { fm ->
            popup.show(fm, null)
        }
    }

}