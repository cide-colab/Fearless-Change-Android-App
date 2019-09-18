package de.thkoeln.colab.fearlesschange.view.label

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.persistance.label.Label
import kotlinx.android.synthetic.main.create_label_dialog.view.*

class CreateLabelDialog(private val labels: List<Label>) : DialogFragment() {

    private var onConfirm: (label: Label) -> Unit = {}

    fun onConfirm(onConfirm: (label: Label) -> Unit) = this.apply {
        this.onConfirm = onConfirm
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.create_label_dialog, null)

        val labelAdapter = LabelAutocompleteAdapter(requireContext(), labels)


        fun getExistingLabel(text: String) =
                labels.find { it.name.trim().equals(text, true) }

        fun updateIcon() {
            val text = view.create_label_dialog_name.text.toString().trim()
            val drawable = getExistingLabel(text)?.let { ColorDrawable(it.color) }
                    ?: resources.getDrawable(R.drawable.ic_add_box_black_24dp)
            view.create_label_dialog_icon.setImageDrawable(drawable)

        }

        view.create_label_dialog_name.threshold = 1
        view.create_label_dialog_name.setAdapter(labelAdapter)
        view.create_label_dialog_name.addTextChangedListener { updateIcon() }
        view.create_label_dialog_name.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            updateIcon()
            hideKeyboard(requireActivity())
        }

        val dialog = MaterialAlertDialogBuilder(requireContext()).setView(view)
                .setTitle(getString(R.string.title_create_lable))
                .setPositiveButton(R.string.action_confirm, null)
                .setNegativeButton(R.string.action_cancel) { dialog, id -> dialog.cancel() }
                .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (view.create_label_dialog_name.text.toString().isNotBlank()) {
                    val text = view.create_label_dialog_name.text.toString().trim()
                    val color = view.create_label_dialog_color_group.getSelectedColor()
                    val label = getExistingLabel(text) ?: Label(text, Color.GRAY)
                    onConfirm(color?.let { label.copy(color = it) } ?: label)
                    dialog.dismiss()
                } else {
                    view.create_label_dialog_name.error = "Badge name cannot be empty"
                }
            }
        }
        return dialog
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}