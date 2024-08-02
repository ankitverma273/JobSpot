package com.example.jobspot.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jobspot.ui.models.JobSurrogate

@Database(
    entities = [JobSurrogate.Job::class],
    version = 1
)

abstract class JobsDatabase: RoomDatabase() {

    abstract fun getJobDao(): JobDao

    companion object {
        @Volatile
        private var instance: JobsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            JobsDatabase::class.java,
            "jobs_db.db"
        ).build()

//        private fun createDatabase(context: Context) = Room.databaseBuilder(
//            context.applicationContext,
//            JobsDatabase::class.java,
//            "jobs_db.db"
//        ).addCallback(DatabaseCallback(context)).build()
    }

//    private class DatabaseCallback(private val context: Context): RoomDatabase.Callback() {
//        override fun onCreate(db: SupportSQLiteDatabase) {
//            super.onCreate(db)
//
//            val scope = CoroutineScope(Dispatchers.IO)
//
//            scope.launch {
//                populateDatabase(context)
//            }
//        }
//
//        private suspend fun populateDatabase(context: Context) {
//            try {
//                val jobData = KtorApi.getOnlyJobs()
//
//                //Insert data into the database
//                instance?.getJobDao()?.insertAllJobs(jobData)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }

}