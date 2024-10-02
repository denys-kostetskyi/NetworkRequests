package com.denyskostetskyi.networkrequests

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.denyskostetskyi.networkrequests.databinding.ActivityMainBinding
import com.denyskostetskyi.networkrequests.domain.model.HttpClient
import com.denyskostetskyi.networkrequests.presentation.viewmodel.MainViewModel
import com.denyskostetskyi.networkrequests.presentation.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var viewModel: MainViewModel

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
    }

    private fun initViewModel() {
        val retrofitRepository =
            (application as NetworkApplication).retrofitWeatherForecastRepository
        val factory = MainViewModelFactory(retrofitRepository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class]
    }

    private fun initViews() {
        setMakeRequestButtonClickListener()
    }

    private fun setMakeRequestButtonClickListener() {
        with(binding) {
            buttonMakeRequest.setOnClickListener {
                when {
                    radioButtonRetrofit.isChecked -> makeRequest(HttpClient.RETROFIT)
                    radioButtonKtor.isChecked -> makeRequest(HttpClient.KTOR)
                    else -> textViewResult.text = getString(R.string.please_select_a_http_client)
                }
            }
        }
    }

    private fun makeRequest(httpClient: HttpClient) {
        when (httpClient) {
            HttpClient.RETROFIT -> Log.d("TEST", "RETROFIT")
            HttpClient.KTOR -> Log.d("TEST", "KTOR")
        }
    }
}
