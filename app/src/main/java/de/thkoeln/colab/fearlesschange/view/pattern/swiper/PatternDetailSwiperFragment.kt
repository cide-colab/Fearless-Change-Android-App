package de.thkoeln.colab.fearlesschange.view.pattern.swiper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveFragment
import de.thkoeln.colab.fearlesschange.view.pattern.detail.PatternDetailViewPagerAdapter
import kotlinx.android.synthetic.main.pattern_detail_swiper_fragment.*


class PatternDetailSwiperFragment : InteractiveFragment<PatternDetailSwiperViewModel>() {

    companion object {
        fun newInstance(patternIds: LongArray, currentPattern: Long = patternIds.getOrNull(0) ?: throw Exception("No Pattern for detail passed")) = PatternDetailSwiperFragment().apply {
            arguments = PatternDetailSwiperFragmentArgs(patternIds, currentPattern).toBundle()
        }
    }

    private val args: PatternDetailSwiperFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(de.thkoeln.colab.fearlesschange.R.layout.pattern_detail_swiper_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val pagerAdapter = PatternDetailViewPagerAdapter(viewModel.patternIds, childFragmentManager)
        pattern_detail_viewpager.adapter = pagerAdapter
        pattern_detail_viewpager.currentItem = viewModel.currentPosition

    }

    override fun createViewModel() = ViewModelProviders.of(this, PatternDetailSwiperViewModelFactory(requireActivity().application, args)).get(PatternDetailSwiperViewModel::class.java)
}
