package com.example.jobspot.ui

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobspot.data.api.KtorApi
import com.example.jobspot.data.db.JobsDatabase
import com.example.jobspot.ui.models.JobSurrogate
import com.example.jobspot.ui.models.States
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    app: Application,
): AndroidViewModel(app) {

//    private val _jobs = MutableStateFlow<List<JobSurrogate.Job>>(emptyList())
//    val jobs = _jobs.asStateFlow()

    private val db = JobsDatabase.invoke(app)
    val jbDao = db.getJobDao()

    val jobs = jbDao.readAllJobs().map {
        States.Loaded(it)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = States.Loading)
    val bookMarkedJobs = jbDao.getAllBookmarkedJobs()

    init {
        viewModelScope.launch {
            try {
                val jobs = KtorApi.getOnlyJobs()
//            _jobs.value = jobs

                //Insert all the jobs in this DAO
                jbDao.insertAllJobsIgnore(jobs)
            } catch (e: Exception) {
                Toast.makeText(app.baseContext, "Network Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    suspend fun getAds(): List<JobSurrogate.Ad> {
        return KtorApi.getOnlyAds()
    }

    fun saveJob(job: JobSurrogate.Job) {
        viewModelScope.launch {
            jbDao.insertJob(job.copy(is_bookmarked = !job.is_bookmarked))
        }

    }

}