package de.thkoeln.colab.fearlesschange.view.more

import android.app.Application
import de.thkoeln.colab.fearlesschange.R
import de.thkoeln.colab.fearlesschange.core.extensions.setSimplePositiveButton
import de.thkoeln.colab.fearlesschange.core.pattern.InteractiveViewModel
import de.thkoeln.colab.fearlesschange.persistance.label.LabelRepository
import de.thkoeln.colab.fearlesschange.persistance.note.NoteRepository
import de.thkoeln.colab.fearlesschange.persistance.pattern.PatternRepository
import de.thkoeln.colab.fearlesschange.persistance.statistic.StatisticAction
import de.thkoeln.colab.fearlesschange.persistance.statistic.StatisticRepository
import kotlinx.coroutines.runBlocking

class MoreViewModel(application: Application) : InteractiveViewModel(application) {


    private val patternRepository by lazy { PatternRepository(application) }
    private val statisticRepository by lazy { StatisticRepository(application) }
    private val noteRepository by lazy { NoteRepository(application) }
    private val labelRepository by lazy { LabelRepository(application) }

    fun resetMostClickedPatternClicked() {
        dialog {
            setMessage(R.string.message_request_confirmation_reset_most_clicked_pattern)
            setSimplePositiveButton(R.string.action_confirm) {
                runBlocking {
                    resetMostClickedPattern()
                    snackBar {
                        setText(R.string.message_most_clicked_pattern_reset)
                    }
                }
            }
        }
    }

    fun resetFavoritesClicked() {
        dialog {
            setMessage(R.string.message_request_confirmation_reset_favorites)
            setSimplePositiveButton(R.string.action_confirm) {
                runBlocking {
                    resetFavorites()
                    snackBar {
                        setText(R.string.message_favorites_reset)
                    }
                }
            }
        }
    }

    fun resetNotesClicked() {
        dialog {
            setMessage(R.string.message_request_confirmation_reset_note)
            setSimplePositiveButton(R.string.action_confirm) {
                runBlocking {
                    resetNotes()
                    snackBar {
                        setText(R.string.message_notes_reset)
                    }
                }
            }
        }
    }

    fun resetLabelsClicked() {
        dialog {
            setMessage(R.string.message_request_confirmation_reset_labels)
            setSimplePositiveButton(R.string.action_confirm) {
                runBlocking {
                    resetLabels()
                    snackBar {
                        setText(R.string.message_labels_reset)
                    }
                }
            }
        }
    }

    fun resetToFactorySettingsClicked() {
        dialog {
            setMessage(R.string.message_request_confirmation_reset_all)
            setSimplePositiveButton(R.string.action_confirm) {
                runBlocking {
                    resetFavorites()
                    resetMostClickedPattern()
                    resetNotes()
                    resetLabels()
                    snackBar {
                        setText(R.string.message_all_reset)
                    }
                }
            }
        }
    }

    private suspend fun resetMostClickedPattern() {
        statisticRepository.deleteAllByAction(StatisticAction.CLICK)
    }

    private suspend fun resetFavorites() {
        patternRepository.setAllFavorites(false)
    }

    private suspend fun resetNotes() {
        noteRepository.deleteAll()
    }

    private suspend fun resetLabels() {
        labelRepository.deleteAll()
    }
}
