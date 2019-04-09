package de.thkoeln.colab.fearlesschange.ui.dashboard.features


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.colab.fearlesschange.data.viewmodel.PatternViewModel
import de.thkoeln.colab.fearlesschange.helper.extensions.nonNullObserve
import de.thkoeln.colab.fearlesschange.ui.adapter.PatternCardPreviewAdapter
import de.thkoeln.colab.fearlesschange.ui.customs.card.PatternCardPreview
import kotlinx.android.synthetic.main.feature_random_pattern.*


class RandomPatternFeature : Fragment() {

    private lateinit var viewModel: PatternViewModel

    private lateinit var adapter1: PatternCardPreviewAdapter
    private lateinit var adapter2: PatternCardPreviewAdapter
    private lateinit var adapter3: PatternCardPreviewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.feature_random_pattern, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(PatternViewModel::class.java)

        adapter1 = PatternCardPreviewAdapter()
        adapter2 = PatternCardPreviewAdapter()
        adapter3 = PatternCardPreviewAdapter()

        adapter1.onCardClickedListener = { cardClicked(it) }
        adapter2.onCardClickedListener = { cardClicked(it) }
        adapter3.onCardClickedListener = { cardClicked(it) }

        random_cards_1.setAdapter(adapter1)
        random_cards_2.setAdapter(adapter2)
        random_cards_3.setAdapter(adapter3)

        viewModel.randomPattern.nonNullObserve(this) {
            changeValues(it)
        }

        random_cards_reload.setOnClickListener {
            viewModel.generateNewRandomPatterns()
        }

    }

    private fun cardClicked(patternInfo: PatternInfo?) {
        viewModel.cardPreviewClicked(
                listOfNotNull(
                        adapter1.pattern?.id,
                        adapter2.pattern?.id,
                        adapter3.pattern?.id
                ).toLongArray(),
                patternInfo?.pattern?.id
        )
    }

    private fun changeValues(info: Pair<List<PatternInfo>, Boolean>) {
        when {
            info.second -> {
                listOf(
                        getAnimation(random_cards_1, 0) { adapter1.change(info.first[0]) },
                        getAnimation(random_cards_2, 1) { adapter2.change(info.first[1]) },
                        getAnimation(random_cards_3, 2) { adapter3.change(info.first[2]) }
                ).forEach { it.start() }
            }
            else -> {
                adapter1.change(info.first[0])
                adapter2.change(info.first[1])
                adapter3.change(info.first[2])
            }
        }
    }

    private fun getAnimation(card: PatternCardPreview, index: Int, delayBetweenAnimations: Long = 100, durationPerAnimation: Long = 100, run: () -> Unit): AnimatorSet {

            val oa1 = ObjectAnimator.ofFloat(card, "scaleX", 1f, 0f).apply { interpolator = DecelerateInterpolator() }
            val oa2 = ObjectAnimator.ofFloat(card, "scaleX", 0f, 1f).apply { interpolator = AccelerateDecelerateInterpolator() }
            val oa3 = ObjectAnimator.ofFloat(card, "scaleX", 1f, 0f).apply { interpolator = DecelerateInterpolator() }
            val oa4 = ObjectAnimator.ofFloat(card, "scaleX", 0f, 1f).apply { interpolator = AccelerateDecelerateInterpolator() }

            oa2.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    run()
                }
            })

            return AnimatorSet().apply {
                playSequentially(oa1, oa2, oa3, oa4)
                duration = durationPerAnimation
                startDelay = delayBetweenAnimations * index
            }
    }
}
