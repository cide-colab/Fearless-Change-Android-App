package de.thkoeln.colab.fearlesschange.view.pattern.detail

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.extensions.observe
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveFragment
import de.thkoeln.colab.fearlesschange.core.shareing.ShareManager
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternPreviewData
import de.thkoeln.colab.fearlesschange.view.patternData.detail.PatternDetailFragmentArgs
import kotlinx.android.synthetic.main.pattern_detail_fragment.*

class PatternDetailFragment : InteractiveFragment<PatternDetailViewModel>() {

    private val args: PatternDetailFragmentArgs by navArgs()
    private var favButton: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.pattern_detail_fragment, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel.pattern.observe(this) { updateView(it) }
        viewModel.sharePatternEvent.observe(this) {
            ShareManager(requireActivity()).sharePattern(it)
        }

        pattern_detail_quick_note.setOnClickListener {
            viewModel.createNoteButtonClicked()
        }

        pattern_detail_notes_btn.setOnClickListener {
            viewModel.showNodesBtnClicked()
        }

    }

    private fun updateView(patternPreviewData: PatternPreviewData) {
        pattern_detail_flippable_pattern_card.pattern = patternPreviewData.pattern
        syncFavBtn()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.pattern_action_menu, menu)
        favButton = menu.findItem(R.id.action_fav)
        syncFavBtn()
    }

    fun syncFavBtn() {
        favButton?.setIcon(viewModel.favButtonIcon)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_fav -> {
                viewModel.favoriteButtonPressed()
                true
            }
            R.id.action_share -> {
                viewModel.sharePressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun createViewModel() = ViewModelProviders.of(this, PatternDetailViewModelFactory(requireActivity().application, args)).get(PatternDetailViewModel::class.java)

    companion object {
        fun newInstance(patternId: Long) = PatternDetailFragment().apply {
            arguments = PatternDetailFragmentArgs(patternId).toBundle()
        }
    }
}




