package de.thkoeln.colab.fearlesschange.ui.notes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.data.viewmodel.NoteViewModel
import de.thkoeln.colab.fearlesschange.helper.SwipeCallback
import de.thkoeln.colab.fearlesschange.helper.extensions.nonNullObserve
import de.thkoeln.colab.fearlesschange.ui.adapter.NoteRecyclerGridAdapter
import kotlinx.android.synthetic.main.fragment_pattern_notes.*


class PatternNotesFragment : Fragment() {

    private var patternId: Long = -1L
    private lateinit var viewModel: NoteViewModel
    private val adapter = NoteRecyclerGridAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        patternId = arguments?.getLong(CARD_ID_KEY) ?: throw IllegalArgumentException("Missing patternId")
        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        viewModel.sendSnackBarMessageEvent.nonNullObserve(this) {
            Snackbar.make(pattern_notes_container, it.message, it.duration).show()
        }

        viewModel.openCreateNoteDialogEvent.nonNullObserve(this) {
            openCreateNoteDialog(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_pattern_notes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNotes()
        pattern_notes_add_note_btn.setOnClickListener {
            viewModel.addNoteButtonClicked(patternId)
        }
    }


    private fun initNotes() {
        pattern_notes_recycler_view.adapter = adapter
        viewModel.getNotesForPattern(patternId).nonNullObserve(this) { adapter.updateNotes(it) }
        val touchCallback = SwipeCallback(ItemTouchHelper.LEFT) { viewHolder, _ ->
            viewHolder?.adapterPosition?.let {
                viewModel.deleteNoteConfirmed(adapter.notes[it])
            }
        }
        ItemTouchHelper(touchCallback).attachToRecyclerView(pattern_notes_recycler_view)
    }

    private fun openCreateNoteDialog(it: Long) {
        val dialog = CreateNoteDialog(context)
        dialog.onConfirmListener = { title, description ->
            viewModel.createNoteConfirmed(it, title, description)
        }
        dialog.show()
    }

    companion object {
        private const val CARD_ID_KEY = "card_id"
        fun newInstance(cardId: Long) =
                PatternNotesFragment().apply {
                    arguments = Bundle().apply {
                        putLong(CARD_ID_KEY, cardId)
                    }
                }
    }

}
