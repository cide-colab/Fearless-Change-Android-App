package de.thkoeln.colab.fearlesschange.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.ui.plugins.BasicPatternFragment
import kotlinx.android.synthetic.main.settings_fragment.*

class SettingsFragment : BasicPatternFragment<SettingsViewModel>() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        reset_most_clicked_item.setOnClickListener { viewModel.resetMostClickedPatternClicked() }
        reset_favorites_item.setOnClickListener { viewModel.resetFavoritesClicked() }
        reset_notes_item.setOnClickListener { viewModel.resetNotesClicked() }
        reset_to_factory_item.setOnClickListener { viewModel.resetToFactorySettingsClicked() }
    }

    override fun createViewModel() = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
}
