package de.thkoeln.colab.fearlesschange.view.pattern.detail

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class PatternDetailViewPagerAdapter(var patternIds: LongArray = longArrayOf(), fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount() = patternIds.size
    override fun getItem(position: Int) = PatternDetailFragment.newInstance(patternIds[position])
}