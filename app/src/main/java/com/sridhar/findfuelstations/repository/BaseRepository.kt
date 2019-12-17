package com.sridhar.findfuelstations.repository

import android.util.Log
import com.sridhar.findfuelstations.api.Result
import retrofit2.Response
import java.io.IOException


open class BaseRepository {

    suspend fun <T : Any> callAdapter(call: suspend () -> Response<T>): T? {
        val result = handleRequest(call)
        var output: T? = null
        when (result) {
            is Result.Success -> output = result.output
            is Result.Error -> Log.e(TAG, result.exception.toString())
        }
        return output
    }

    private suspend fun <T : Any> handleRequest(
        call: suspend () -> Response<T>
    ): Result<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful)
                Result.Success(response.body()!!)
            else
                Result.Error(IOException(response.errorBody().toString()))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    companion object {
        val TAG = BaseRepository::class.java.simpleName
    }
}