package de.thkoeln.colab.fearlesschange.view.dashboard.plugins

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.nonNullObserve
import de.thkoeln.colab.fearlesschange.core.onAnimationEnd
import de.thkoeln.colab.fearlesschange.core.pattern.PatternViewModelFragment
import de.thkoeln.colab.fearlesschange.core.playSequentially
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.view.pattern.preview.PatternPreviewViewHolder
import kotlinx.android.synthetic.main.random_pattern_fragment.*


class RandomPatternFragment : PatternViewModelFragment<RandomPatternViewModel>() {

    companion object {
        fun newInstance(groupId: Int = 0) = RandomPatternFragment().apply {
            arguments = RandomPatternFragmentArgs(groupId).toBundle()
        }
    }

    private val adapter1 = PatternPreviewViewHolder()
    private val adapter2 = PatternPreviewViewHolder()
    private val adapter3 = PatternPreviewViewHolder()

    private val args: RandomPatternFragmentArgs by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.random_pattern_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter1.patternClickedListener = viewModel.patternCardClicked
        adapter2.patternClickedListener = viewModel.patternCardClicked
        adapter3.patternClickedListener = viewModel.patternCardClicked

        adapter1.inflate(random_cards_container_1, true)
        adapter2.inflate(random_cards_container_2, true)
        adapter3.inflate(random_cards_container_3, true)

        viewModel.randomPattern.nonNullObserve(this) { info -> changeValues(info) }

        random_cards_reload.setOnClickListener { viewModel.shuffleClicked() }
    }


    private fun changeValues(info: Triple<PatternInfo, PatternInfo, PatternInfo>) {
        if (viewModel.shouldAnimatePattern) animateAndChangePattern(info)
        else changePattern(info)

    }

    private fun changePattern(info: Triple<PatternInfo, PatternInfo, PatternInfo>) {
        adapter1.bind(info.first)
        adapter2.bind(info.second)
        adapter3.bind(info.third)
    }

    private fun animateAndChangePattern(info: Triple<PatternInfo, PatternInfo, PatternInfo>) {
        getAnimation(random_cards_container_1, 0) { adapter1.bind(info.first) }.start()
        getAnimation(random_cards_container_2, 1) { adapter2.bind(info.second) }.start()
        getAnimation(random_cards_container_3, 2) { adapter3.bind(info.third) }.start()
    }

    private fun getAnimation(card: View, index: Int, delayBetweenAnimations: Long = 100, durationPerAnimation: Long = 100, run: () -> Unit): AnimatorSet {

        val oa1 = ObjectAnimator.ofFloat(card, "scaleX", 1f, 0f).apply { interpolator = DecelerateInterpolator() }
        val oa2 = ObjectAnimator.ofFloat(card, "scaleX", 0f, 1f).apply { interpolator = AccelerateDecelerateInterpolator() }
        val oa3 = ObjectAnimator.ofFloat(card, "scaleX", 1f, 0f).apply { interpolator = DecelerateInterpolator() }
        val oa4 = ObjectAnimator.ofFloat(card, "scaleX", 0f, 1f).apply { interpolator = AccelerateDecelerateInterpolator() }

        oa2.onAnimationEnd { run() }

        return AnimatorSet().playSequentially(durationPerAnimation, delayBetweenAnimations * index, oa1, oa2, oa3, oa4)
    }

    override fun createViewModel() = ViewModelProviders.of(requireActivity(), RandomPatternViewModelFactory(requireActivity().application, args)).get(RandomPatternViewModel::class.java)
}
