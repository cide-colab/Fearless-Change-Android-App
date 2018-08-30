package de.thkoeln.fherborn.fearlesschange.ui.dashboard.features


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.fherborn.fearlesschange.data.viewmodel.PatternViewModel
import de.thkoeln.fherborn.fearlesschange.ui.adapter.PatternCardPreviewAdapter
import de.thkoeln.fherborn.fearlesschange.ui.customs.card.PatternCardPreview
import kotlinx.android.synthetic.main.feature_random_pattern.*


class RandomPatternFeature : Fragment() {

    private var animated = true
    private lateinit var viewModel: PatternViewModel

    private lateinit var patternCardAdapters: List<PatternCardPreviewAdapter>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.feature_random_pattern, container, false)

    override fun onResume() {
        super.onResume()

        viewModel = ViewModelProviders.of(activity!!).get(PatternViewModel::class.java)
        viewModel.getRandomPatterns().observe(this, Observer { onPatternChanged(it) })

        random_cards_reload.setOnClickListener {
            animated = false
            viewModel.generateNewRandomPatterns()
        }
        viewModel.generateNewRandomPatterns()

        val patternCards by lazy {
            listOf(
                    random_cards_1,
                    random_cards_2,
                    random_cards_3
            )
        }
        patternCardAdapters = listOf(
                PatternCardPreviewAdapter(),
                PatternCardPreviewAdapter(),
                PatternCardPreviewAdapter()
        )

        patternCardAdapters.forEachIndexed { index, patternCardPreviewAdapter ->
            patternCardPreviewAdapter.onCardClickedListener = {
                viewModel.cardPreviewClicked(patternCardAdapters.map { pca ->
                    pca.pattern?.id
                }.filterNotNull().toLongArray(), it?.pattern?.id)
            }
            patternCards[index].setAdapter(patternCardPreviewAdapter)
        }
    }

    private fun onPatternChanged(patterns: List<PatternInfo>?) {
        when {
            animated -> patterns?.forEachIndexed { index, patternInfo ->
                patternCardAdapters[index].change(patternInfo)
            }
            else -> {
                animateCardsAndRun(random_cards_1, random_cards_2, random_cards_3) {
                    patternCardAdapters[it].change(patterns?.get(it))
                }
                animated = true
            }
        }

    }

    private fun animateCardsAndRun(vararg cards: PatternCardPreview, delayBetweenAnimations: Long = 100, durationPerAnimation: Long = 100, run: (Int) -> Unit) {

        val animators = cards.mapIndexed { index, cardView ->
            val oa1 = ObjectAnimator.ofFloat(cardView, "scaleX", 1f, 0f).apply {
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        run(index)
                    }
                })
            }
            val oa2 = ObjectAnimator.ofFloat(cardView, "scaleX", 0f, 1f).apply { interpolator = AccelerateDecelerateInterpolator() }
            val oa3 = ObjectAnimator.ofFloat(cardView, "scaleX", 1f, 0f).apply { interpolator = DecelerateInterpolator() }
            val oa4 = ObjectAnimator.ofFloat(cardView, "scaleX", 0f, 1f).apply { interpolator = AccelerateDecelerateInterpolator() }

            AnimatorSet().apply {
                playSequentially(oa1, oa2, oa3, oa4)
                duration = durationPerAnimation
                startDelay = delayBetweenAnimations * index
            }
        }
        animators.forEach { it.start() }
    }
}
