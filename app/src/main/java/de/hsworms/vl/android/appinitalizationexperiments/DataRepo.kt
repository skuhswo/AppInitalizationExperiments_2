package de.hsworms.vl.android.appinitalizationexperiments

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepo @Inject constructor(): InitializableRepo() {

    private var message: String? = null

    override suspend fun _initialize() {
        Thread.sleep(5000L)
        message = "Message from Data Repo"
    }

    fun getString(): String {
        if (message != null)
            return(message as String)
        else
            throw Exception()
    }

}