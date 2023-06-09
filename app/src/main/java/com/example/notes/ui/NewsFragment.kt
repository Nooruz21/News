package com.example.notes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.note.databinding.FragmentNewsBinding
import com.example.notes.App
import com.example.notes.model.News


class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            save()
        }
        rename()

    }
        private fun rename() {
            val editText = arguments?.getString("key1")
            binding.editText.setText(editText)
    }

    private fun save() {
        val text =binding.editText.text.toString()
        val news = News(0,text,System.currentTimeMillis())
        App.database.newsDao().insert(news)
        App.database.newsDao().update(news)
        val bundle= bundleOf("news" to news)
        parentFragmentManager.setFragmentResult("rk_news",bundle)
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}