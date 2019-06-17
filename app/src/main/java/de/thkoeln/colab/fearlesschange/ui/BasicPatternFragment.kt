package de.thkoeln.colab.fearlesschange.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import de.thkoeln.colab.fearlesschange.observe

abstract class BasicPatternFragment<T : BasicPatternViewModel> : Fragment() {
    lateinit var viewModel: T


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = createViewModel()
        viewModel.actionEvent.switchObserve(this) { findNavController().navigate(it) }
        viewModel.snackbarEvent.switchObserve(this) { message ->
            view?.let { Snackbar.make(it, message.message, message.duration).show() }
        }

        viewModel.requestConfirmationEvent.observe(this) {
            AlertDialog.Builder(requireContext())
                    .setTitle(it.title)
                    .setMessage(it.text)
                    .setPositiveButton(it.positiveButtonText) { _, _ -> viewModel.onConfirmRequest(it) }
                    .setNegativeButton(it.negativeButtonText) { _, _ -> viewModel.onCancelRequest(it) }
                    .create().show()
        }

    }

    abstract fun createViewModel(): T
}