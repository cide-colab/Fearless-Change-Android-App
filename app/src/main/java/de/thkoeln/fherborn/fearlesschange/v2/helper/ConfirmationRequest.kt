package de.thkoeln.fherborn.fearlesschange.v2.helper

import java.util.*

data class ConfirmationRequest(
        val title: String?,
        val text: String,
        val positiveButtonText: String,
        val negativeButtonText: String,
        val id: UUID
)