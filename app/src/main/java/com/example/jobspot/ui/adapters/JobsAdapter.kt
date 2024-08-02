package com.example.jobspot.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jobspot.R
import com.example.jobspot.databinding.JobsItemLayoutBinding
import com.example.jobspot.ui.models.JobSurrogate

object JobComparator : DiffUtil.ItemCallback<JobSurrogate.Job>() {
    override fun areItemsTheSame(oldItem: JobSurrogate.Job, newItem: JobSurrogate.Job): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: JobSurrogate.Job, newItem: JobSurrogate.Job): Boolean {
        return oldItem == newItem
    }
}

class JobsAdapter(private val itemClick: (JobSurrogate.Job) -> Unit, private val bookMarkClicked: (JobSurrogate.Job) -> Unit): PagingDataAdapter<JobSurrogate.Job, JobsViewHolder>(JobComparator) {
    override fun onBindViewHolder(holder: JobsViewHolder, position: Int) {
        val job = getItem(position)
        holder.bind(job!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsViewHolder {
        return JobsViewHolder(JobsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false), itemClick, bookMarkClicked)
    }

}

//class JobsAdapter(private val jobList: List<JobSurrogate.Job>, private val itemClick: (JobSurrogate.Job) -> Unit, private val bookMarkClicked: (JobSurrogate.Job) -> Unit): RecyclerView.Adapter<JobsViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsViewHolder {
//
//        return JobsViewHolder(JobsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false), itemClick, bookMarkClicked)
//    }
//
//    //Size of the Dataset which is constant here that is 10
//    override fun getItemCount() = jobList.size
//
//    override fun onBindViewHolder(holder: JobsViewHolder, position: Int) {
//        val job: JobSurrogate.Job = jobList[position]
//        holder.bind(job)
//    }
//}
//

//
//}

class JobsViewHolder(private val binding: JobsItemLayoutBinding, val onItemClick: (JobSurrogate.Job) -> Unit, val onClickBookMark: (JobSurrogate.Job) -> Unit): RecyclerView.ViewHolder(binding.root) {

    fun bind(job: JobSurrogate.Job) {
        binding.tvTitle.text = job.job_role
        binding.tvSalary.text = job.primary_details.Salary
        binding.tvLocation.text = job.primary_details.Place
        binding.tvPhoneNumber.text = job.whatsapp_no

        binding.btnView.setOnClickListener {
            onItemClick(job)
        }

        if (job.is_bookmarked) {
            binding.ivBookMark.setImageResource(R.drawable.ic_bookmarks_filled)
        } else {
            binding.ivBookMark.setImageResource(R.drawable.ic_bookmarks_outlined)
        }

        binding.ivBookMark.setOnClickListener {
            onClickBookMark(job)
        }

    }
}