package de.thkoeln.colab.fearlesschange.core.extensions

import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun MaterialAlertDialogBuilder.setSimplePositiveButton(textId: Int, listener: () -> Unit) = setPositiveButton(textId) { _, _ -> listener() }
fun MaterialAlertDialogBuilder.setSimpleNegativeButton(textId: Int, listener: () -> Unit) = setNegativeButton(textId) { _, _ -> listener() }