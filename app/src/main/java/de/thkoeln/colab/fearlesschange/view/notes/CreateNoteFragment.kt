package de.thkoeln.colab.fearlesschange.view.notes

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.navArgs

import de.thkoeln.colab.fearlesschange.R
import kotlinx.android.synthetic.main.create_note_fragment.*

class CreateNoteFragment : Fragment() {

    private val args: CreateNoteFragmentArgs by navArgs()

    companion object {
        fun newInstance() = CreateNoteFragment()
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

}
