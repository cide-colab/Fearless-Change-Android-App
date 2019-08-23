package de.thkoeln.colab.fearlesschange.view.pattern.detail

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.observe
import de.thkoeln.colab.fearlesschange.core.pattern.PatternViewModelFragment
import de.thkoeln.colab.fearlesschange.core.shareing.ShareManager
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternInfo
import kotlinx.android.synthetic.main.pattern_detail_fragment.*

class PatternDetailFragment : PatternViewModelFragment<PatternDetailViewModel>() {

    private val args: PatternDetailFragmentArgs by navArgs()
    private var favButton: MenuItem? = null
    private val adapter = PatternDetailViewHolder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.pattern_detail_fragment, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter.inflate(pattern_detail_container, true)

        viewModel.pattern.observe(this) { updateView(it) }
        viewModel.sharePatternEvent.observe(this) {
            ShareManager(requireActivity()).sharePattern(it)
        }

    }

    private fun updateView(patternInfo: PatternInfo) {
        adapter.bind(patternInfo)
        syncFavBtn()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.pattern_detail_action_menu, menu)
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




