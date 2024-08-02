package com.example.jobspot.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.jobspot.R
import com.example.jobspot.data.api.KtorApi
import com.example.jobspot.databinding.FragmentBookmarksBinding
import com.example.jobspot.ui.MainViewModel
import com.example.jobspot.ui.adapters.BookmarksAdapter
import com.example.jobspot.ui.adapters.JobsAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.encodeToString

class BookmarksFragment : Fragment() {

    private lateinit var binding: FragmentBookmarksBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBookmarksBinding.inflate(inflater, container, false)

        viewModel.bookMarkedJobs.onEach { job ->
            val adapter = BookmarksAdapter(job, { item ->
                val action =
                    BookmarksFragmentDirections.actionBookmarksFragmentToJobDetailsFragment(
                        KtorApi.json.encodeToString(item)
                    )
                findNavController().navigate(action)
            }, { item ->
                viewModel.saveJob(item)
            })
            binding.bookmarksRecyclerView.adapter = adapter
        }.launchIn(lifecycleScope)

        return binding.root
    }

}