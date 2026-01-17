package com.frc135.match_collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.frc135.match_collection.databinding.PreloadedFragmentBinding
import com.frc135.match_collection.objective.CollectionObjectiveActivity

class PreloadedFragment : Fragment(R.layout.preloaded_fragment) {

    private var _binding: PreloadedFragmentBinding? = null
    private val binding get() = _binding!!

    private val collectionObjectiveActivity get() = activity as CollectionObjectiveActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = PreloadedFragmentBinding.inflate(inflater, container, false)

        with(binding) {
            togglePreloaded.isChecked = preloaded
            scoring = preloaded
        }

        initOnClicks()
        return binding.root
    }
    /**
     * Changes the preloaded piece based on the button pressed
     * Changes the screen to scoring/auto depending on the changed preload
     */
    private fun initOnClicks() {
        with(binding) {
            togglePreloaded.setOnClickListener {
                preloaded = !preloaded
                scoring = !scoring
                collectionObjectiveActivity.enableButtons()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // prevent memory leaks
    }
}