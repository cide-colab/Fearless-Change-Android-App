package de.thkoeln.fherborn.fearlesschange.ui.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.persistance.repositories.CardRepository
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast


/**
 * A [PreferenceActivity] that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 *
 * See [Android Design: Settings](http://developer.android.com/design/patterns/settings.html)
 * for design guidelines and the [Settings API Guide](http://developer.android.com/guide/topics/ui/settings.html)
 * for more information on developing a Settings UI.
 */
class SettingsActivity : AppActivity(), View.OnClickListener {


    private lateinit var cardRepository: CardRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        cardRepository = CardRepository(application)

        setlisteners()
    }

    private fun setlisteners() {
        reset_most_clicked.setOnClickListener(this)
        reset_favorites.setOnClickListener(this)
        reset_to_factory.setOnClickListener(this)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_activity_actions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View?) =
            when(view?.id) {
                R.id.reset_most_clicked -> showAlert()
                R.id.reset_favorites -> Toast.makeText(this, "reset_favorites", Toast.LENGTH_SHORT).show()
                R.id.reset_to_factory -> Toast.makeText(this, "reset_to_factory", Toast.LENGTH_SHORT).show()
                else -> {
                    throw NullPointerException()
                }
            }

    fun showAlert() {

        alert("Please confirm this operation.") {
            title = "Warning"
            yesButton { toast("success") }
            noButton { toast("canceled")  }
        }.show()
    }


}
