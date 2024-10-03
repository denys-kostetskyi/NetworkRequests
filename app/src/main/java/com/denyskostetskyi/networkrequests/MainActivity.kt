package com.denyskostetskyi.networkrequests

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.denyskostetskyi.networkrequests.databinding.ActivityMainBinding
import com.denyskostetskyi.networkrequests.domain.model.Location
import com.denyskostetskyi.networkrequests.presentation.state.WeatherForecastUiState
import com.denyskostetskyi.networkrequests.presentation.viewmodel.MainViewModel
import com.denyskostetskyi.networkrequests.presentation.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var viewModel: MainViewModel

    private val exportFileLauncher =
        registerForActivityResult(ActivityResultContracts.CreateDocument(MIME_TYPE_XLSX)) { uri: Uri? ->
            uri?.let {
                downloadFile(it)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViewModel()
        initViews()
        observeViewModel()
    }

    private fun initViewModel() {
        val retrofitRepository =
            (application as NetworkApplication).retrofitWeatherForecastRepository
        val ktorRepository =
            (application as NetworkApplication).ktorWeatherForecastRepository
        val factory = MainViewModelFactory(retrofitRepository, ktorRepository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class]
    }

    private fun initViews() {
        setMakeRequestButtonClickListener()
        setDownloadFileButtonClickListener()
    }

    private fun setMakeRequestButtonClickListener() {
        with(binding) {
            buttonMakeRequest.setOnClickListener {
                when {
                    radioButtonRetrofit.isChecked ->
                        viewModel.fetchWeatherForecastRetrofit(Location.LVIV)

                    radioButtonKtor.isChecked -> viewModel.fetchWeatherForecastKtor(Location.LVIV)
                    else -> textViewResult.text = getString(R.string.please_select_a_http_client)
                }
            }
        }
    }

    private fun setDownloadFileButtonClickListener() {
        with(binding) {
            buttonDownloadFile.setOnClickListener {
                if (!radioButtonRetrofit.isChecked && !radioButtonKtor.isChecked) {
                    textViewResult.text = getString(R.string.please_select_a_http_client)
                } else {
                    exportFileLauncher.launch(SUGGESTED_FILE_NAME)
                }
            }
        }
    }

    private fun downloadFile(destination: Uri) {
        with(binding) {
            when {
                radioButtonRetrofit.isChecked -> viewModel.downloadWeatherForecastFileRetrofit(
                    Location.LVIV,
                    contentResolver,
                    destination
                )

                radioButtonKtor.isChecked -> viewModel.downloadWeatherForecastFileKtor(
                    Location.LVIV,
                    contentResolver,
                    destination
                )

                else -> {}
            }
        }
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(this) { state ->
            updateUiStateLoading(state is WeatherForecastUiState.Loading)
            when (state) {
                is WeatherForecastUiState.Success -> displayResult(state.result)
                is WeatherForecastUiState.Error -> showError(state.error)
                WeatherForecastUiState.Loading -> {}
            }
        }
    }

    private fun displayResult(result: String) {
        binding.textViewResult.text = result
    }

    private fun updateUiStateLoading(isLoading: Boolean) {
        with(binding) {
            progressBar.isVisible = isLoading
            buttonMakeRequest.isEnabled = !isLoading
        }
    }

    private fun showError(error: Throwable?) {
        binding.textViewResult.text = error.toString()
    }

    companion object {
        private const val SUGGESTED_FILE_NAME = "forecast"
        private const val MIME_TYPE_XLSX =
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    }
}
