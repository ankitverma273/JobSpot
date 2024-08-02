package com.example.jobspot.ui.models

sealed class States {

    data object Loading: States()

    data class Loaded(val jobs: List<JobSurrogate.Job>): States()

}