package de.thkoeln.fherborn.fearlesschange.ui.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import de.thkoeln.fherborn.fearlesschange.ui.patterndetail.FlippableCardFragment


class PatternDetailViewPagerAdapter(var patternIds: LongArray = longArrayOf(), fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount() = patternIds.size
    override fun getItem(position: Int) = FlippableCardFragment.newInstance(patternIds[position])
}