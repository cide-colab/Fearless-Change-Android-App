package de.thkoeln.colab.fearlesschange.ui.pattern.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import de.thkoeln.colab.fearlesschange.observe
import de.thkoeln.colab.fearlesschange.ui.plugins.BasicPatternFragment

class PatternPreviewFragment : BasicPatternFragment<PatternPreviewViewModel>() {

    private val adapter = PatternPreviewAdapter()

    companion object {
        fun newInstance(patternId: Long) = PatternPreviewFragment().apply {
            arguments = PatternPreviewFragmentArgs(patternId).toBundle()
        }
    }

    private val args: PatternPreviewFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            adapter.inflate(container, requireContext())

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = PatternPreviewAdapter(viewModel.patternCardClicked)

        viewModel.pattern.observe(this) { patternInfo -> adapter.bind(patternInfo) }
    }

    override fun createViewModel() = ViewModelProviders.of(this, PatternPreviewViewModelFactory(requireActivity().application, args)).get(PatternPreviewViewModel::class.java)
}
