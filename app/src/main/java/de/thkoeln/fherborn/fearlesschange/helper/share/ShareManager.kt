package de.thkoeln.fherborn.fearlesschange.helper.share

import android.app.Activity
import android.support.v4.app.ShareCompat
import de.thkoeln.fherborn.fearlesschange.data.persistance.pattern.Pattern
import android.content.Intent




object ShareManager {
    fun sharePattern(activity: Activity, pattern: Pattern) {
//
//        val shareIntent = ShareCompat.IntentBuilder.from(activity)
//                .setType("text/html")
//                .setHtmlText("""
//                    <p>${pattern.title}</p>
//                    <p>${pattern.summary}</p>
//                """.trimIndent())
//                .setSubject("Definitely read this")
//                .intent
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, pattern.summary)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Take a look on this pattern. This may help you!")
        shareIntent.putExtra(Intent.EXTRA_TITLE, pattern.title)
        activity.startActivity(shareIntent)

    }
}