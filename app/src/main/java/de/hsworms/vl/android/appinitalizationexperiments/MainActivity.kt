package de.hsworms.vl.android.appinitalizationexperiments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var progressIndicator: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressIndicator = findViewById(R.id.progressBar)

        ensureViewModelInitalized()
    }

    private val onViewModelInitializedObserver = Observer<Boolean> { initialized ->
        if (initialized) {
            Toast.makeText(
                baseContext, "initialization complete: " + viewModel.getString(),
                Toast.LENGTH_SHORT
            ).show()
            
            progressIndicator.visibility = View.INVISIBLE
        }
    }

    private fun ensureViewModelInitalized() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        if ((viewModel.isInitialized.value == null) || (viewModel.isInitialized.value == false)) {
            viewModel.isInitialized.observe(this, onViewModelInitializedObserver)
            viewModel.initialize()
        }
    }

    override fun onResume() {
        super.onResume()

        ensureViewModelInitalized()
    }

}