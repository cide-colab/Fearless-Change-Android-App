package de.thkoeln.colab.fearlesschange.ui.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import de.thkoeln.colab.fearlesschange.ui.pattern.detail.PatternDetailFragment


class PatternDetailViewPagerAdapter(var patternIds: LongArray = longArrayOf(), fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount() = patternIds.size
    override fun getItem(position: Int) = PatternDetailFragment.newInstance(patternIds[position])
}