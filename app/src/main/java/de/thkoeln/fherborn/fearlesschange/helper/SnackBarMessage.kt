package de.thkoeln.fherborn.fearlesschange.helper

/**
 * Created by Florian on 24.08.2018.
 */
data class SnackBarMessage(private val m: Any, val duration: Int = 2000) {
    val message = m.toString()
}