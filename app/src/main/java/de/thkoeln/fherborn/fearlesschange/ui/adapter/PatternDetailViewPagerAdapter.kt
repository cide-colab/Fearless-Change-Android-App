package de.thkoeln.fherborn.fearlesschange.ui.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import de.thkoeln.fherborn.fearlesschange.data.persistance.pattern.PatternInfo
import de.thkoeln.fherborn.fearlesschange.ui.patterndetail.FlippableCardFragment


class PatternDetailViewPagerAdapter(var pattern: List<PatternInfo> = listOf(), fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount() = pattern.size
    override fun getItem(position: Int) = FlippableCardFragment.newInstance(pattern[position].pattern.id)
}