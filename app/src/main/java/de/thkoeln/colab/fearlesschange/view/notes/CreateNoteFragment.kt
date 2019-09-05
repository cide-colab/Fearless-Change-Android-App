package de.thkoeln.colab.fearlesschange.view.notes

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.toPx
import jp.wasabeef.richeditor.RichEditor
import kotlinx.android.synthetic.main.create_note_fragment.*


class RichTextEditorToolbar : HorizontalScrollView {

    var editor: RichEditor? = null

    private lateinit var container: LinearLayout

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        container = LinearLayout(context)
        container.orientation = LinearLayout.HORIZONTAL
        addView(container)

        addAction(R.drawable.ic_undo_black_24dp) { undo() }
        addAction(R.drawable.ic_redo_black_24dp) { redo() }
        addAction(R.drawable.ic_format_bold_black_24dp) { setBold() }
        addAction(R.drawable.ic_format_italic_black_24dp) { setItalic() }
        addAction(R.drawable.ic_format_underlined_black_24dp) { setUnderline() }
        addAction(R.drawable.ic_format_strikethrough_black_24dp) { setStrikeThrough() }
        addAction(R.drawable.ic_subscript_solid) { setSubscript() }
        addAction(R.drawable.ic_superscript_solid) { setSuperscript() }
        addAction(R.drawable.ic_h1) { setHeading(1) }
        addAction(R.drawable.ic_h2) { setHeading(2) }
        addAction(R.drawable.ic_h3) { setHeading(3) }
        addAction(R.drawable.ic_h4) { setHeading(4) }
        addAction(R.drawable.ic_h5) { setHeading(5) }
        addAction(R.drawable.ic_h6) { setHeading(6) }
//        addAction(R.drawable.ic_format_color_text_black_24dp){ setTextColor() }
//        addAction(R.drawable.ic_format_color_fill_black_24dp){ setTextBackgroundColor() }
        addAction(R.drawable.ic_format_indent_increase_black_24dp) { setIndent() }
        addAction(R.drawable.ic_format_indent_decrease_black_24dp) { setOutdent() }
        addAction(R.drawable.ic_format_align_left_black_24dp) { setAlignLeft() }
        addAction(R.drawable.ic_format_align_center_black_24dp) { setAlignCenter() }
        addAction(R.drawable.ic_format_align_right_black_24dp) { setAlignRight() }
        addAction(R.drawable.ic_format_list_bulleted_black_24dp) { setBullets() }
        addAction(R.drawable.ic_format_list_numbered_black_24dp) { setNumbers() }
        addAction(R.drawable.ic_format_quote_black_24dp) { setBlockquote() }
//        addAction(R.drawable.ic_insert_photo_black_24dp){ insertImage() }
//        addAction(R.drawable.ic_insert_link_black_24dp){ insertLink() }
        addAction(R.drawable.ic_check_box_black_24dp) { insertTodo() }

    }

    private fun addAction(icon: Int, onClick: RichEditor.(view: View) -> Unit) {
        val params = LayoutParams(24.toPx(), 24.toPx())
        params.leftMargin = 2.toPx()
        params.rightMargin = 2.toPx()

        val button = ImageButton(context)
        button.setImageResource(icon)
        button.layoutParams = params
        button.setBackgroundResource(android.R.color.transparent)
        button.setOnClickListener { editor?.onClick(it) }
        container.addView(button)
    }
}

class CreateNoteFragment : Fragment() {

    private val args: CreateNoteFragmentArgs by navArgs()

    companion object {
        fun newInstance() = CreateNoteFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private lateinit var viewModel: CreateNoteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.create_note_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = createViewModel()

        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)

        editor_toolbar.editor = editor

    }

    private fun createViewModel() = ViewModelProviders.of(this, CreateNoteViewModelFactory(requireActivity().application, args)).get(CreateNoteViewModel::class.java)


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_create_note -> {
//                viewModel.onSaveClicked(visual.toHtml())

                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)

                requireActivity().onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
