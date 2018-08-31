package de.thkoeln.fherborn.fearlesschange.ui.patterndetail

import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.thkoeln.fherborn.fearlesschange.R
import de.thkoeln.fherborn.fearlesschange.data.viewmodel.PatternDetailViewModel
import de.thkoeln.fherborn.fearlesschange.helper.extensions.nonNullObserve
import de.thkoeln.fherborn.fearlesschange.helper.share.ShareManager
import de.thkoeln.fherborn.fearlesschange.ui.adapter.PatternDetailViewPagerAdapter
import de.thkoeln.fherborn.fearlesschange.ui.notes.PatternNotesFragment
import kotlinx.android.synthetic.main.pattern_detail_dialog.*


/**
 * A DialogFragment to show a flippable pattern detail with its notes
 */
class PatternDetailDialogFragment : DialogFragment() {

    var onDismissListener: ((Long) -> Unit)? = null

    private lateinit var viewModel: PatternDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.pattern_detail_dialog, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        back_btn.setOnClickListener { dismiss() }


        viewModel = ViewModelProviders.of(this).get(PatternDetailViewModel::class.java)
        viewModel.extractSelectedPattern(arguments, SELECTED_PATTERN_ID_KEY)
        viewModel.extractPatternIdList(arguments, PATTERN_IDS_KEY)


        val pagerAdapter = PatternDetailViewPagerAdapter(fragmentManager = childFragmentManager)

        pattern_detail_viewpager.adapter = pagerAdapter
        viewModel.setupPagingAdapterEvent.nonNullObserve(this) {
            pagerAdapter.patternIds = it
            pagerAdapter.notifyDataSetChanged()
        }

        viewModel.setViewPagerPositionEvent.nonNullObserve(this) {
            pattern_detail_viewpager.currentItem = it
        }


        pattern_detail_viewpager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                viewModel.onSwipePager(pagerAdapter.patternIds[position])
            }
        })


        viewModel.selectedPatternInfo.nonNullObserve(this) {
            card_detail_notes_count.text = it.noteCount.toString()
            ShareManager.sharePattern(activity!!, it.pattern)
        }


        viewModel.selectedPatternId.nonNullObserve(this) {
            arguments?.putLong(SELECTED_PATTERN_ID_KEY, it)
            childFragmentManager
                    .beginTransaction()
                    .replace(R.id.pattern_detail_notes_container, PatternNotesFragment.newInstance(it))
                    .commit()

        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        onDismissListener?.invoke(viewModel.selectedPatternId.value?:0)
        super.onDismiss(dialog)
    }

    companion object {
        private const val PATTERN_IDS_KEY = "card_id"
        private const val SELECTED_PATTERN_ID_KEY = "selected_card_id"
        fun newInstance(patternIds: LongArray, selectedPatternId: Long) =
                PatternDetailDialogFragment().apply {
                    arguments = Bundle().apply {
                        putLongArray(PATTERN_IDS_KEY, patternIds)
                        putLong(SELECTED_PATTERN_ID_KEY, selectedPatternId)
                    }
                }
    }
}