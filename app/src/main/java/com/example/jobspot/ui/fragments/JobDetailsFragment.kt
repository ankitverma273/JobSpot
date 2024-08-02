package com.example.jobspot.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.jobspot.data.api.KtorApi
import com.example.jobspot.databinding.FragmentJobDetailsBinding
import com.example.jobspot.ui.models.JobSurrogate
import com.example.jobspot.utils.extractTitleAndDetails

class JobDetailsFragment : Fragment() {

    private lateinit var binding: FragmentJobDetailsBinding
    private val args: JobDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentJobDetailsBinding.inflate(inflater, container, false)

        val jobData = KtorApi.json.decodeFromString<JobSurrogate.Job>(args.Job)

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.tvAppBarTitle.text = jobData.job_role
        binding.tvJobTitle.text = jobData.job_role
        binding.tvCompanyName.text = jobData.company_name
        binding.tvCompanyDesc.text = extractTitleAndDetails(jobData.other_details)
        binding.tvLocationMain.text = jobData.primary_details.Place
        binding.tvSalaryMain.text = jobData.primary_details.Salary
        binding.tvPhoneMain.text = jobData.whatsapp_no
        binding.tvApplicationsMain.text = jobData.num_applications.toString()
        binding.tvExperienceMain.text = jobData.primary_details.Experience
        binding.tvQualificationsMain.text = jobData.primary_details.Qualification
        binding.tvJobCategoryMain.text = jobData.job_category

        return binding.root
    }

}