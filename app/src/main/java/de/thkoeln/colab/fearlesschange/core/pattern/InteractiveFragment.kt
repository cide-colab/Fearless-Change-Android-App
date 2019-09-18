package de.thkoeln.colab.fearlesschange.core.pattern

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import de.thkoeln.colab.fearlesschange.core.extensions.makeSnackbar


abstract class InteractiveFragment<T : InteractiveViewModel> : Fragment() {
    lateinit var viewModel: T


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = createViewModel()
        viewModel.navigationEvent.switchObserve(this) { build -> findNavController().build() }
        viewModel.showSnackBarEvent.switchObserve(this) { build -> view?.makeSnackbar()?.build()?.show() }
        viewModel.showDialogEvent.switchObserve(this) { build -> MaterialAlertDialogBuilder(requireContext()).build().show() }

    }

    abstract fun createViewModel(): T
}