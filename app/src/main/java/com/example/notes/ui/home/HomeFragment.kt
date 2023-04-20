package com.example.notes.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.note.R
import com.example.note.databinding.FragmentHomeBinding
import com.example.notes.App
import com.example.notes.model.News

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: NewsAdapter


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = NewsAdapter()
        val list= App.database.newsDao().getAll()
        adapter.addItems(list)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.newsFragment)
        }
        binding.crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }


        parentFragmentManager.setFragmentResultListener(
            "rk_news",
            viewLifecycleOwner
        ) { _, bundle ->
            val news = bundle.getSerializable("news") as News
            Log.e("Home", "text=$news")
            adapter.addItem(news)
        }
        binding.recyclerView.adapter = adapter
        rename()
        alert()
    }
    private fun alert() {
        adapter.onLongClick = {

            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Удалить эту новость")
            dialog.setMessage("Вы точно хотите удалить эту новость?")
            dialog.setPositiveButton("Да") { _, _ ->

                adapter.deleteItemsAndNotifyAdapter(it)
                binding.recyclerView.adapter = adapter
                //Delete items in RecyclerView**

            }
            dialog.setNegativeButton("Назад") { dialog, _ -> dialog.cancel() }
            dialog.show()
        }
    }

    private fun rename() {
        adapter.onClick={
            val bundle=Bundle()
            bundle.putString("key1",it.title)
            findNavController().navigate(R.id.newsFragment,bundle)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionSort) {
            getSortedList()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getSortedList() {
        val list: List<News> = App.database.newsDao().getAllSortedTitle()
        adapter.addItems(list)
    }
}