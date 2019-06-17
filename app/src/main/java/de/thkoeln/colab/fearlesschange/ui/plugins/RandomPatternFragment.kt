package de.thkoeln.colab.fearlesschange.ui.plugins

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
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.nonNullObserve
import de.thkoeln.colab.fearlesschange.onAnimationEnd
import de.thkoeln.colab.fearlesschange.playSequentially
import de.thkoeln.colab.fearlesschange.ui.adapter.PatternCardPreviewAdapter
import de.thkoeln.colab.fearlesschange.ui.customs.card.PatternCardPreview
import kotlinx.android.synthetic.main.feature_random_pattern.*


class RandomPatternFragment : BasicPatternFragment<RandomPatternViewModel>() {

    companion object {
        fun newInstance(groupId: Int = 0) = RandomPatternFragment().apply {
            arguments = RandomPatternFragmentArgs(groupId).toBundle()
        }
    }

    private val adapter1 = PatternCardPreviewAdapter()
    private val adapter2 = PatternCardPreviewAdapter()
    private val adapter3 = PatternCardPreviewAdapter()

    private val args: RandomPatternFragmentArgs by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.random_pattern_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter1.onCardClickedListener = viewModel.patternCardClicked
        adapter2.onCardClickedListener = viewModel.patternCardClicked
        adapter3.onCardClickedListener = viewModel.patternCardClicked

        random_cards_1.setAdapter(adapter1)
        random_cards_2.setAdapter(adapter2)
        random_cards_3.setAdapter(adapter3)

        viewModel.randomPattern.nonNullObserve(this) { info -> changeValues(info) }

        random_cards_reload.setOnClickListener { viewModel.shuffleClicked() }
    }


    private fun changeValues(info: Triple<PatternInfo, PatternInfo, PatternInfo>) {
        if (viewModel.shouldAnimatePattern) animateAndChangePattern(info)
        else changePattern(info)

    }

    private fun changePattern(info: Triple<PatternInfo, PatternInfo, PatternInfo>) {
        adapter1.change(info.first)
        adapter2.change(info.second)
        adapter3.change(info.third)
    }

    private fun animateAndChangePattern(info: Triple<PatternInfo, PatternInfo, PatternInfo>) {
        getAnimation(random_cards_1, 0) { adapter1.change(info.first) }.start()
        getAnimation(random_cards_2, 1) { adapter2.change(info.second) }.start()
        getAnimation(random_cards_3, 2) { adapter3.change(info.third) }.start()
    }

    private fun getAnimation(card: PatternCardPreview, index: Int, delayBetweenAnimations: Long = 100, durationPerAnimation: Long = 100, run: () -> Unit): AnimatorSet {

        val oa1 = ObjectAnimator.ofFloat(card, "scaleX", 1f, 0f).apply { interpolator = DecelerateInterpolator() }
        val oa2 = ObjectAnimator.ofFloat(card, "scaleX", 0f, 1f).apply { interpolator = AccelerateDecelerateInterpolator() }
        val oa3 = ObjectAnimator.ofFloat(card, "scaleX", 1f, 0f).apply { interpolator = DecelerateInterpolator() }
        val oa4 = ObjectAnimator.ofFloat(card, "scaleX", 0f, 1f).apply { interpolator = AccelerateDecelerateInterpolator() }

        oa2.onAnimationEnd { run() }

        return AnimatorSet().playSequentially(durationPerAnimation, delayBetweenAnimations * index, oa1, oa2, oa3, oa4)
    }

    override fun createViewModel() = ViewModelProviders.of(requireActivity(), RandomPatternViewModelFactory(requireActivity().application, args)).get(RandomPatternViewModel::class.java)
}
