package de.thkoeln.colab.fearlesschange.view.dashboard.plugins

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.extensions.nonNullObserve
import de.thkoeln.colab.fearlesschange.core.extensions.playSequentially
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveFragment
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternPreviewData
import kotlinx.android.synthetic.main.random_pattern_fragment.*


class RandomPatternFragment : InteractiveFragment<RandomPatternViewModel>() {

    companion object {
        fun newInstance(groupId: Int = 0) = RandomPatternFragment().apply {
            arguments = RandomPatternFragmentArgs(groupId).toBundle()
        }
    }
    private val args: RandomPatternFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.random_pattern_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        random_pattern_pattern_card_1.setOnClickListener { viewModel.patternCardClicked(random_pattern_pattern_card_1.patternPreviewData) }
        random_pattern_pattern_card_2.setOnClickListener { viewModel.patternCardClicked(random_pattern_pattern_card_2.patternPreviewData) }
        random_pattern_pattern_card_3.setOnClickListener { viewModel.patternCardClicked(random_pattern_pattern_card_3.patternPreviewData) }

        viewModel.randomPattern.nonNullObserve(this) { info -> changeValues(info) }

        random_cards_reload.setOnClickListener { viewModel.shuffleClicked() }
    }


    private fun changeValues(previewData: Triple<PatternPreviewData, PatternPreviewData, PatternPreviewData>) {
        if (viewModel.shouldAnimatePattern) animateAndChangePattern(previewData)
        else changePattern(previewData)

    }

    private fun changePattern(previewData: Triple<PatternPreviewData, PatternPreviewData, PatternPreviewData>) {
        random_pattern_pattern_card_1.patternPreviewData = previewData.first
        random_pattern_pattern_card_2.patternPreviewData = previewData.second
        random_pattern_pattern_card_3.patternPreviewData = previewData.third
    }

    private fun animateAndChangePattern(previewData: Triple<PatternPreviewData, PatternPreviewData, PatternPreviewData>) {
        getAnimation(random_pattern_pattern_card_1, 0) { random_pattern_pattern_card_1.patternPreviewData = previewData.first }.start()
        getAnimation(random_pattern_pattern_card_2, 1) { random_pattern_pattern_card_2.patternPreviewData = previewData.second }.start()
        getAnimation(random_pattern_pattern_card_3, 2) { random_pattern_pattern_card_3.patternPreviewData = previewData.third }.start()
    }

    private fun getAnimation(card: View, index: Int, delayBetweenAnimations: Long = 100, durationPerAnimation: Long = 100, run: () -> Unit): AnimatorSet {

        val oa1 = ObjectAnimator.ofFloat(card, "scaleX", 1f, 0f).apply { interpolator = DecelerateInterpolator() }
        val oa2 = ObjectAnimator.ofFloat(card, "scaleX", 0f, 1f).apply { interpolator = AccelerateDecelerateInterpolator() }
        val oa3 = ObjectAnimator.ofFloat(card, "scaleX", 1f, 0f).apply { interpolator = DecelerateInterpolator() }
        val oa4 = ObjectAnimator.ofFloat(card, "scaleX", 0f, 1f).apply { interpolator = AccelerateDecelerateInterpolator() }

        oa2.doOnEnd { run() }

        return AnimatorSet().playSequentially(durationPerAnimation, delayBetweenAnimations * index, oa1, oa2, oa3, oa4)
    }

    override fun createViewModel() = ViewModelProviders.of(requireActivity(), RandomPatternViewModelFactory(requireActivity().application, args)).get(RandomPatternViewModel::class.java)
}
