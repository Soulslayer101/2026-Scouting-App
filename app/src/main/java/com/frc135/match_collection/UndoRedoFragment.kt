package com.frc135.match_collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.frc135.match_collection.databinding.UndoRedoFragmentBinding
import com.frc135.match_collection.objective.CollectionObjectiveActivity

class UndoRedoFragment : Fragment(R.layout.undo_redo_fragment) {

    /**
     * The main view of this fragment.
     */
    private var _binding: UndoRedoFragmentBinding? = null
    private val binding get() = _binding!!

    private val collectionObjectiveActivity get() = activity as CollectionObjectiveActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = UndoRedoFragmentBinding.inflate(inflater, container, false)

        initOnClicks()
        enableButtons()

        return binding.root
    }

    /**
     * Removes the last timeline action when the undo button is clicked
     * Replaces the last timeline action that was removed when the redo button is clicked
     */
    private fun initOnClicks() {
        with(binding) {
            btnUndo.setOnClickListener {
                val newPressTime = System.currentTimeMillis()
                if (buttonPressedTime + 250 < newPressTime) {
                    buttonPressedTime = newPressTime
                    collectionObjectiveActivity.timelineRemove()
                }
            }

            btnRedo.setOnClickListener {
                val newPressTime = System.currentTimeMillis()
                if (buttonPressedTime + 250 < newPressTime) {
                    buttonPressedTime = newPressTime
                    collectionObjectiveActivity.timelineReplace()
                }
            }
        }
    }

    private fun updateUndoRedo() {
        with(binding) {
            val undoText = resources.getText(R.string.btn_undo)
            btnUndo.text = if (timeline.isEmpty()) {
                undoText
            } else {
                if (timeline.size >= 2) {
                    "$undoText\n" + (if (timeline[timeline.size - 2]["action_type"] == Constants.ActionType.FAIL.toString()) "Fail "
                    else "") + timeline.last()["action_type"]?.split('_')?.joinToString(" ") {
                            it.lowercase().replaceFirstChar { char -> char.uppercaseChar() }
                        }
                } else {
                    "$undoText\n" + timeline.last()["action_type"]?.split('_')?.joinToString(" ") {
                            it.lowercase().replaceFirstChar { char -> char.uppercaseChar() }
                        }
                }
            }
            // Get the "Redo" text
            val redoText = resources.getText(R.string.btn_redo)
            btnRedo.text = if (collectionObjectiveActivity.removedTimelineActions.isEmpty()) {
                // Nothing to redo
                redoText
            } else {
                if (collectionObjectiveActivity.removedTimelineActions.size >= 2) {
                    "$redoText\n" + collectionObjectiveActivity.removedTimelineActions.last()["action_type"]?.split(
                            '_'
                        )?.joinToString(" ") {
                            it.lowercase().replaceFirstChar { char -> char.uppercaseChar() }
                        } + if (collectionObjectiveActivity.removedTimelineActions.last()["action_type"] == Constants.ActionType.FAIL.toString()) {
                        " " + collectionObjectiveActivity.removedTimelineActions[collectionObjectiveActivity.removedTimelineActions.size - 2]["action_type"]?.split(
                                '_'
                            )?.joinToString(" ") {
                                it.lowercase().replaceFirstChar { char -> char.uppercaseChar() }
                            }
                    } else ""
                } else {
                    "$redoText\n" + collectionObjectiveActivity.removedTimelineActions.last()["action_type"]?.split(
                            '_'
                        )?.joinToString(" ") {
                            it.lowercase().replaceFirstChar { char -> char.uppercaseChar() }
                    }
                }
            }
        }
    }

    fun enableButtons() {
        with(binding) {
            updateUndoRedo()

            btnUndo.isEnabled = timeline.size > 0
            btnRedo.isEnabled = collectionObjectiveActivity.removedTimelineActions.size > 0
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}