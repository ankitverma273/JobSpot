package com.example.jobspot.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jobspot.ui.models.JobSurrogate
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {

    @Query("SELECT * FROM jobs")
    fun pagedReadAllJobs(): PagingSource<Int, JobSurrogate.Job>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllJobs(jobs: List<JobSurrogate.Job>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllJobsIgnore(jobs: List<JobSurrogate.Job>)

    @Query("SELECT * FROM jobs WHERE id = 10049")
    fun readAllJobs(): Flow<List<JobSurrogate.Job>>

    @Query("SELECT * FROM jobs WHERE is_bookmarked")
    fun getAllBookmarkedJobs(): Flow<List<JobSurrogate.Job>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobSurrogate.Job)

    @Delete
    suspend fun deleteJob(job: JobSurrogate.Job)

    @Query("SELECT * FROM jobs WHERE id = :jobId")
    suspend fun getJobById(jobId: Int): JobSurrogate.Job?
}