package de.thkoeln.colab.fearlesschange.helper.share

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.RelativeLayout
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.persistance.pattern.Pattern
import de.thkoeln.colab.fearlesschange.getResourceId
import de.thkoeln.colab.fearlesschange.ui.LayoutAdapter
import kotlinx.android.synthetic.main.pattern_print_layout.view.*

class PrintPatternAdapter : LayoutAdapter<Pattern>(R.layout.pattern_print_layout) {
    override fun bind(view: View, value: Pattern) {
        with(view) {
            print_pattern_card_front_title.text = value.title
            print_pattern_card_front_summary.text = value.summary
            print_pattern_card_front_image.setImageResource(context.getResourceId(value.pictureName, "drawable")
                    ?: R.drawable.default_pattern_image)
            print_pattern_card_back_title.text = value.title
            print_pattern_card_back_problem.text = value.problem
            print_pattern_card_back_solution.text = value.solution
            layoutParams = RelativeLayout.LayoutParams(convertDpToPixel(800f).toInt(), WRAP_CONTENT)
        }
    }

    private fun convertDpToPixel(dp: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return Math.round(px).toFloat()
    }
}