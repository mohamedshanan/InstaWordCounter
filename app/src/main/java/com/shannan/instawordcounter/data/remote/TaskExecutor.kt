package com.shannan.instawordcounter.data.remote

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

abstract class ProgressTask<P, R> {
    abstract fun runTask(progressToken: OnTaskProgressUpdate<P>): R?
}

typealias Task<R> = Callable<R>

typealias OnTaskProgressUpdate<P> = (P) -> Unit

typealias OnTaskComplete<R> = (R) -> Unit

class TaskExecutor {
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    fun <R> executeTask(task: Task<R>, onComplete: OnTaskComplete<R>) {
        executor.execute {
            val result = task.call()
            handler.post {
                onComplete(result)
            }
        }
    }

    fun <R> executeTask(task: Callable<R>): Future<R>? {
        return executor.submit(task)
    }

    fun <P, R> executeProgressTask(
        progressTask: ProgressTask<P, R>,
        onProgress: OnTaskProgressUpdate<P>,
        onComplete: OnTaskComplete<R?>
    ) {
        executor.execute {
            val result = progressTask.runTask(progressToken = { progress ->
                handler.post {
                    onProgress(progress)
                }
            })

            handler.post {
                onComplete(result)
            }
        }
    }
}