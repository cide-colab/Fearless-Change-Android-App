package de.thkoeln.colab.fearlesschange.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.viewmodel.SettingsViewModel
import de.thkoeln.colab.fearlesschange.helper.extensions.nonNullObserve
import de.thkoeln.colab.fearlesschange.ui.AppActivity
import de.thkoeln.colab.fearlesschange.ui.search.SearchActivity
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(action_bar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)

        viewModel.sendSnackBarMessageEvent.nonNullObserve(this) {
            Snackbar.make(activity_wrapper, it.message, it.duration).show()
        }

        viewModel.requestConfirmationEvent.nonNullObserve(this) {
            AlertDialog.Builder(this)
                    .setTitle(it.title)
                    .setMessage(it.text)
                    .setPositiveButton(it.positiveButtonText) { _, _ -> viewModel.onConfirmRequest(it)}
                    .setNegativeButton(it.negativeButtonText) { _, _ -> viewModel.onCancelRequest(it)}
                    .create().show()
        }

        reset_most_clicked_item.setOnClickListener{viewModel.resetMostClickedPatternClicked()}
        reset_favorites_item.setOnClickListener{viewModel.resetFavoritesClicked()}
        reset_notes_item.setOnClickListener {viewModel.resetNotesClicked()}
        reset_to_factory_item.setOnClickListener{viewModel.resetToFactorySettingsClicked()}

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
