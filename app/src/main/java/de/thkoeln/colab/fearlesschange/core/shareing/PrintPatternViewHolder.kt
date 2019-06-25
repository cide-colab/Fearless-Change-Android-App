package de.thkoeln.colab.fearlesschange.core.shareing

import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.RelativeLayout
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.getResourceId
import de.thkoeln.colab.fearlesschange.core.layout.LayoutViewHolder
import de.thkoeln.colab.fearlesschange.core.toPx
import de.thkoeln.colab.fearlesschange.persistance.pattern.Pattern
import kotlinx.android.synthetic.main.pattern_print_layout.view.*

class PrintPatternViewHolder : LayoutViewHolder<Pattern>(R.layout.pattern_print_layout) {
    override fun bind(view: View, value: Pattern) {
        with(view) {
            val image = context.getResourceId(value.pictureName, "drawable")
                    ?: R.drawable.default_pattern_image
            print_front_title.text = value.title
            print_front_summary.text = value.summary
            print_front_image.setImageResource(image)
            print_back_image.setImageResource(image)
            print_back_title.text = value.title
            print_back_problem.text = value.problem
            print_back_solution.text = value.solution
            layoutParams = RelativeLayout.LayoutParams(800.toPx(), WRAP_CONTENT)
        }
    }
}