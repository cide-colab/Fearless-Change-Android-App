package de.thkoeln.colab.fearlesschange.view.notes

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import de.thkoeln.colab.fearlesschange.R
import kotlinx.android.synthetic.main.create_note_fragment.*

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
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

        create_note_title.requestFocus()

    }

    private fun createViewModel() = ViewModelProviders.of(this, CreateNoteViewModelFactory(requireActivity().application, args)).get(CreateNoteViewModel::class.java)


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_create_note -> {
                viewModel.onCreateNoteClicked(create_note_title.text.toString(), create_note_note.text.toString())
                requireActivity().onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
