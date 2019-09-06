package de.thkoeln.colab.fearlesschange.view.notes

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.toPx
import de.thkoeln.colab.fearlesschange.persistance.label.Label
import de.thkoeln.colab.fearlesschange.persistance.todos.Todo
import jp.wasabeef.richeditor.RichEditor
import kotlinx.android.synthetic.main.create_label_dialog.view.*
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

        addAction(R.drawable.ic_undo_black_24dp) { editor?.undo() }
        addAction(R.drawable.ic_redo_black_24dp) { editor?.redo() }
        addAction(R.drawable.ic_format_bold_black_24dp) { editor?.setBold() }
        addAction(R.drawable.ic_format_italic_black_24dp) { editor?.setItalic() }
        addAction(R.drawable.ic_format_underlined_black_24dp) { editor?.setUnderline() }
        addAction(R.drawable.ic_format_strikethrough_black_24dp) { editor?.setStrikeThrough() }
        addAction(R.drawable.ic_subscript_solid) { editor?.setSubscript() }
        addAction(R.drawable.ic_superscript_solid) { editor?.setSuperscript() }
        addAction(R.drawable.ic_h1) { editor?.setHeading(1) }
        addAction(R.drawable.ic_h2) { editor?.setHeading(2) }
        addAction(R.drawable.ic_h3) { editor?.setHeading(3) }
        addAction(R.drawable.ic_h4) { editor?.setHeading(4) }
        addAction(R.drawable.ic_h5) { editor?.setHeading(5) }
        addAction(R.drawable.ic_h6) { editor?.setHeading(6) }
//        addAction(R.drawable.ic_format_color_text_black_24dp){ setTextColor() }
//        addAction(R.drawable.ic_format_color_fill_black_24dp){ setTextBackgroundColor() }
        addAction(R.drawable.ic_format_indent_increase_black_24dp) { editor?.setIndent() }
        addAction(R.drawable.ic_format_indent_decrease_black_24dp) { editor?.setOutdent() }
        addAction(R.drawable.ic_format_align_left_black_24dp) { editor?.setAlignLeft() }
        addAction(R.drawable.ic_format_align_center_black_24dp) { editor?.setAlignCenter() }
        addAction(R.drawable.ic_format_align_right_black_24dp) { editor?.setAlignRight() }
        addAction(R.drawable.ic_format_list_bulleted_black_24dp) { editor?.setBullets() }
        addAction(R.drawable.ic_format_list_numbered_black_24dp) { editor?.setNumbers() }
        addAction(R.drawable.ic_format_quote_black_24dp) { editor?.setBlockquote() }
//        addAction(R.drawable.ic_insert_photo_black_24dp){ insertImage() }
//        addAction(R.drawable.ic_insert_link_black_24dp){ insertLink() }
//        addAction(R.drawable.ic_check_box_black_24dp) { insertTodo() }

    }

    fun addAction(icon: Int, index: Int = container.childCount, onClick: (view: View) -> Unit) {
        val params = LayoutParams(48.toPx(), 48.toPx())
//        params.leftMargin = 2.toPx()
//        params.rightMargin = 2.toPx()

        val button = ImageButton(context)
        button.setPadding(4.toPx(), 4.toPx(), 4.toPx(), 4.toPx())
        button.setImageResource(icon)
        button.layoutParams = params
        button.setBackgroundResource(android.R.color.transparent)
        button.setOnClickListener { onClick(it) }
        container.addView(button, index)
    }
}

class CreateNoteFragment : Fragment() {

    private val args: CreateNoteFragmentArgs by navArgs()

    private lateinit var labelAdapter: LabelRecyclerAdapter
    private lateinit var createCheckboxAdapter: CreateCheckboxRecyclerAdapter

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

//        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)

        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START

        labelAdapter = LabelRecyclerAdapter(requireContext())

        label_container.adapter = labelAdapter
//        label_container.layoutManager = GridLayoutManager(context, 2)
        label_container.layoutManager = layoutManager

//        repeat(label_container.itemDecorationCount) {
//            label_container.removeItemDecorationAt(0)
//        }

        createCheckboxAdapter = CreateCheckboxRecyclerAdapter(requireContext())
        todo_container.adapter = createCheckboxAdapter
        todo_container.layoutManager = GridLayoutManager(context, 2)

        editor.setPlaceholder(getString(R.string.note_hint))
        editor_toolbar.editor = editor
        editor_toolbar.addAction(R.drawable.ic_check_box_black_24dp, 0) { createCheckboxAdapter.addItem(Todo(false, "", 0)) }
        editor_toolbar.addAction(R.drawable.ic_label_black_24dp, 1) { createLabel { labelAdapter.addItem(it) } }


    }

    private fun createLabel(callback: (label: Label) -> Unit) {
        val view = requireActivity().layoutInflater.inflate(R.layout.create_label_dialog, null)
        val dialog = AlertDialog.Builder(requireContext()).setView(view)
                .setTitle(getString(R.string.title_create_lable))
                .setPositiveButton(R.string.action_confirm, null)
                .setNegativeButton(R.string.action_cancel) { dialog, id -> dialog.cancel() }
                .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (view.create_label_dialog_name.text.toString().isNotBlank()) {
                    val text = view.create_label_dialog_name.text.toString()
                    val color = view.create_label_dialog_color_group.getSelectedColor()
                    callback(Label(text, color))
                    dialog.dismiss()
                } else {
                    view.create_label_dialog_name.error = "Label name cannot be empty"
                }
            }
        }

        dialog.show()
    }

    private fun createViewModel() = ViewModelProviders.of(this, CreateNoteViewModelFactory(requireActivity().application, args)).get(CreateNoteViewModel::class.java)


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_create_note -> {

                viewModel.onSaveClicked(labelAdapter.items, editor.html
                        ?: "", createCheckboxAdapter.items)

                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)

                requireActivity().onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
