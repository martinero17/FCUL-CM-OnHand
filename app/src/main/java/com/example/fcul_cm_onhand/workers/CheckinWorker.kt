package com.example.fcul_cm_onhand.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class CheckInWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams)  {

    override fun doWork(): Result {
        Log.i("CheckInWorker", "Work was performed")
        //TODO("Send notification --> notification docs: https://developer.android.com/develop/ui/views/notifications")

        return Result.success()
    }
}