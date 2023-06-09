package com.example.notes.ui.board

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.note.R
import com.example.note.databinding.FragmentBoardBinding
import com.example.notes.Prefs
import me.relex.circleindicator.CircleIndicator3

class BoardFragment : Fragment() {

    private lateinit var binding: FragmentBoardBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BoardAdapter(requireContext(),findNavController())
        binding.viewPager.adapter = adapter
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            activity?.finish()
        }
        binding.skipped.setOnClickListener {
            Prefs(requireContext()).saveState()
            findNavController().navigateUp()
        }
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val indicator : CircleIndicator3? = activity?.findViewById(R.id.circle)
        indicator?.setViewPager(binding.viewPager)

    }

}