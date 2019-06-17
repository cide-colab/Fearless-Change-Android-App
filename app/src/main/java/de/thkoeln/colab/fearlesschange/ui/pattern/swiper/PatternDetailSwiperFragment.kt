package de.thkoeln.colab.fearlesschange.ui.pattern.swiper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.ui.adapter.PatternDetailViewPagerAdapter
import de.thkoeln.colab.fearlesschange.ui.plugins.BasicPatternFragment
import kotlinx.android.synthetic.main.pattern_detail_swiper_fragment.*


class PatternDetailSwiperFragment : BasicPatternFragment<PatternDetailSwiperViewModel>() {

    companion object {
        fun newInstance(patternIds: LongArray, currentPattern: Long = patternIds.getOrNull(0) ?: throw Exception("No Pattern for detail passed")) = PatternDetailSwiperFragment().apply {
            arguments = PatternDetailSwiperFragmentArgs(patternIds, currentPattern).toBundle()
        }
    }

    private val args: PatternDetailSwiperFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pattern_detail_swiper_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val pagerAdapter = PatternDetailViewPagerAdapter(viewModel.patternIds, childFragmentManager)
        pattern_detail_viewpager.adapter = pagerAdapter
        pattern_detail_viewpager.currentItem = viewModel.currentPosition

//        pattern_detail_viewpager.setOnTouchListener { v, _ ->
//            v.parent.requestDisallowInterceptTouchEvent(true)
//            false
//        }
//
//        pattern_detail_viewpager.onPageScrolled { _: Int, _: Float, _: Int ->
//            pattern_detail_viewpager.parent.requestDisallowInterceptTouchEvent(true)
//        }
//        viewModel.setupPagingAdapterEvent.observe(this) {
//            pagerAdapter.patternIds = it
//            pagerAdapter.notifyDataSetChanged()
//        }

//        pattern_detail_viewpager.onPageScrolled { position, _, _ -> viewModel.onSwipePager(position) }
//
//
//        viewModel.currentPattern.nonNullObserve(this) {
//            //card_detail_notes_count.text = it.noteCount.toString()
//            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
//        }
//
//        viewModel.currentPattern.nonNullObserve(this) {
//            //TODO show notes
//            //arguments?.putLong(PatternDetailDialogFragment.SELECTED_PATTERN_ID_KEY, it)
////            childFragmentManager
////                    .beginTransaction()
////                    .replace(R.id.pattern_detail_notes_container, PatternNotesFragment.newInstance(it))
////                    .commit()
//
//        }
//
//        viewModel.sharePatternEvent.nonNullObserve(this) {
//            ShareManager(requireActivity()).sharePattern(it)
//        }

        //share_btn.setOnClickListener { viewModel.sharePressed() }

    }

    override fun createViewModel() = ViewModelProviders.of(this, PatternDetailSwiperViewModelFactory(requireActivity().application, args)).get(PatternDetailSwiperViewModel::class.java)
}
