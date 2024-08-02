package com.example.jobspot.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jobspot.R
import com.example.jobspot.databinding.BookmarksItemLayoutBinding
import com.example.jobspot.ui.models.JobSurrogate

class BookmarksAdapter(private val bookmarkList: List<JobSurrogate.Job>, private val itemClick: (JobSurrogate.Job) -> Unit, private val onBookMarkClick: (JobSurrogate.Job) -> Unit): RecyclerView.Adapter<BookmarksViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksViewHolder {
        return BookmarksViewHolder(BookmarksItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false), itemClick, onBookMarkClick)
    }

    override fun getItemCount() = bookmarkList.size

    override fun onBindViewHolder(holder: BookmarksViewHolder, position: Int) {
        val bookMarked = bookmarkList[position]
        holder.bind(bookMarked)
    }
}

class BookmarksViewHolder(private val binding: BookmarksItemLayoutBinding, private val onItemClick: (job: JobSurrogate.Job) -> Unit, private val onBookMarkClick: (JobSurrogate.Job) -> Unit): RecyclerView.ViewHolder(binding.root) {

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

            onBookMarkClick(job)
        }
    }

}