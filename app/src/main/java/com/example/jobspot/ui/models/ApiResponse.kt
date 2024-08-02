package com.example.jobspot.ui.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.json.JSONObject

@Serializable
data class ApiResponse(
    val results: List<JobSurrogate>,
)

@Serializable
data class PrimaryDetails(
    val Place: String,
    val Salary: String,
    val Experience: String,
    val Qualification: String
)

@Serializable
data class Creatives(
    val order_id: Int,
    val image_url: String
)

@Serializable
sealed class JobSurrogate() {

    @Serializable
    @SerialName("1009")
    @Entity(
        tableName = "jobs"
    )
    data class Job(
        @PrimaryKey
        val id: Int,
        @Embedded val primary_details: PrimaryDetails,
        val is_bookmarked: Boolean,
        val job_role: String,
        val other_details: String,
        val job_hours: String,
        val job_category: String,
        val num_applications: Int,
        val whatsapp_no: String,
        val company_name: String,
    ): JobSurrogate()

    @Serializable
    @SerialName("1040")
    data class Ad(
        val creatives: List<Creatives>
    ): JobSurrogate()

}