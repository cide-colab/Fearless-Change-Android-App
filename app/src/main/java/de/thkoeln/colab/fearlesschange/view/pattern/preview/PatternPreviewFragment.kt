package de.thkoeln.colab.fearlesschange.view.pattern.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.extensions.getDrawableId
import de.thkoeln.colab.fearlesschange.core.extensions.observe
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveFragment
import kotlinx.android.synthetic.main.pattern_preview.view.*

class PatternPreviewFragment : InteractiveFragment<PatternPreviewViewModel>() {

    companion object {
        fun newInstance(patternId: Long) = PatternPreviewFragment().apply {
            arguments = PatternPreviewFragmentArgs(patternId).toBundle()
        }
    }

    private val args: PatternPreviewFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            LayoutInflater.from(container?.context).inflate(R.layout.pattern_preview, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.pattern.observe(this) { info ->
            with(info.pattern) {
                view?.pattern_preview_image?.setImageResource(context?.getDrawableId(pictureName)
                        ?: R.drawable.default_pattern_image)
                view?.pattern_preview_title?.text = title
                view?.pattern_preview_summary?.text = summary
                view?.pattern_preview_favorite_icon?.visibility = if (favorite) VISIBLE else GONE
                view?.pattern_preview_notes_count?.text = info.noteCount.toString()
                view?.pattern_preview_notes_count?.visibility = if (info.noteCount > 0) VISIBLE else GONE
                view?.pattern_preview_notes_icon?.visibility = if (info.noteCount > 0) VISIBLE else GONE
                view?.pattern_preview_card?.setOnClickListener { viewModel.patternCardClicked(info) }
            }

        }
    }

    override fun createViewModel() = ViewModelProviders.of(this, PatternPreviewViewModelFactory(requireActivity().application, args)).get(PatternPreviewViewModel::class.java)
}
