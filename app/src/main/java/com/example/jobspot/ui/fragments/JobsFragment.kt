package com.example.jobspot.ui.fragments
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import com.example.jobspot.data.api.KtorApi
import com.example.jobspot.databinding.FragmentJobsBinding
import com.example.jobspot.ui.MainViewModel
import com.example.jobspot.ui.adapters.JobsAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.encodeToString

class JobsFragment : Fragment() {

    private lateinit var binding: FragmentJobsBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentJobsBinding.inflate(inflater, container, false)

        try {
            checkIfFirstLaunch()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show()
        }

        val pagingAdapter = JobsAdapter(
            { item ->
                val action = JobsFragmentDirections.actionJobsFragmentToJobDetailsFragment(KtorApi.json.encodeToString(item))
                findNavController().navigate(action)
            }, { item ->
                viewModel.saveJob(item)
            }
        )
        binding.jobsRecyclerView.adapter = pagingAdapter

// Activities can use lifecycleScope directly; fragments use
// viewLifecycleOwner.lifecycleScope.
//        lifecycleScope.launch {
//            viewModel.flow.collectLatest { pagingData ->
//                pagingAdapter.submitData(pagingData)
//            }
//        }

        viewModel.pagedJobs.onEach {
            pagingAdapter.submitData(it)
        }.launchIn(lifecycleScope)
//        viewModel.jobs.onEach { job ->
//            when (job) {
//                is States.Loaded -> {
//                    binding.progressBar.visibility = View.GONE
//                    pagingAdapter.submitData(job.jobs)
//                }
//                States.Loading -> binding.progressBar.visibility = View.VISIBLE
//            }
//
//        }.launchIn(lifecycleScope)

        return binding.root
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun isFirstLaunch(context: Context): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("is_first_launch", true)
        if (isFirstLaunch) {
            sharedPreferences.edit().putBoolean("is_first_launch", false).apply()
        }
        return isFirstLaunch
    }

    private fun checkIfFirstLaunch(){
        // Check if it's the first launch
        if (isFirstLaunch(requireContext())) {
            // Check if the internet is available
            if (!isInternetAvailable(requireContext())) {
                // Show error message
                showNoInternetDialog()
            }
        }
    }

    private fun showNoInternetDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("No Internet Connection")
            .setMessage("Internet connection is required on the first launch. Please connect to the internet and restart the app.")
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
                System.exit(0)
            }
            .show()
    }


}