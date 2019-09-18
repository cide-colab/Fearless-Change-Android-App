package de.thkoeln.colab.fearlesschange.view.more

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveFragment
import kotlinx.android.synthetic.main.more_fragment.*


class MoreFragment : InteractiveFragment<MoreViewModel>() {

    companion object {
        fun newInstance() = MoreFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.more_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        more_reset_most_clicked_container.setOnClickListener { viewModel.resetMostClickedPatternClicked() }
        more_reset_favorites_container.setOnClickListener { viewModel.resetFavoritesClicked() }
        more_reset_notes_container.setOnClickListener { viewModel.resetNotesClicked() }
        more_reset_labels_container.setOnClickListener { viewModel.resetLabelsClicked() }
        more_reset_all_container.setOnClickListener { viewModel.resetToFactorySettingsClicked() }
        more_more_btn.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://fearlesschangepatterns.com/")))
        }
    }

    override fun createViewModel() = ViewModelProviders.of(this).get(MoreViewModel::class.java)
}
