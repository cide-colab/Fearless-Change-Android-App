package de.thkoeln.colab.fearlesschange.ui.pattern.detail

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.helper.animation.FlipAnimationManager
import de.thkoeln.colab.fearlesschange.helper.extensions.getResourceId
import de.thkoeln.colab.fearlesschange.helper.share.ShareManager
import de.thkoeln.colab.fearlesschange.observe
import de.thkoeln.colab.fearlesschange.ui.notes.PatternNotesFragment
import de.thkoeln.colab.fearlesschange.ui.plugins.BasicPatternFragment
import kotlinx.android.synthetic.main.pattern_detail_fragment.*

class PatternDetailFragment : BasicPatternFragment<PatternDetailViewModel>() {

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

        childFragmentManager.beginTransaction().add(pattern_detail_notes_container.id, PatternNotesFragment.newInstance(viewModel.patternId)).commit()

        val flipAnimationHelper = FlipAnimationManager(pattern_detail_front, pattern_detail_back)
        pattern_detail_front.setOnClickListener { flipAnimationHelper.flipToBack() }
        pattern_detail_back.setOnClickListener { flipAnimationHelper.flipToFront() }

    }

    private fun updateView(patternInfo: PatternInfo) {
        updateFront(patternInfo)
        updateBack(patternInfo)
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

    private fun updateFront(patternInfo: PatternInfo) {
        with(patternInfo.pattern) {
            pattern_detail_front_title.text = title
            pattern_detail_front_summary.text = summary
            pattern_detail_front_image.setImageResource(context?.getResourceId(pictureName, "drawable") ?: R.drawable.default_pattern_image)
        }
    }

    private fun updateBack(patternInfo: PatternInfo) {
        with(patternInfo.pattern) {
            pattern_detail_back_title.text = title
            pattern_detail_back_problem.text = problem
            pattern_detail_back_solution.text = solution
        }
    }

    override fun createViewModel() = ViewModelProviders.of(this, PatternDetailViewModelFactory(requireActivity().application, args)).get(PatternDetailViewModel::class.java)

    companion object {
        fun newInstance(patternId: Long) = PatternDetailFragment().apply {
            arguments = PatternDetailFragmentArgs(patternId).toBundle()
        }
    }
}


