package com.denyskostetskyi.networkrequests

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.denyskostetskyi.networkrequests.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initViews()
    }

    private fun initViews() {
        setMakeRequestButtonClickListener()
    }

    private fun setMakeRequestButtonClickListener() {
        with(binding) {
            buttonMakeRequest.setOnClickListener {
                val isRetrofitSelected = radioButtonRetrofit.isChecked
                val isKtorSelected = radioButtonKtor.isChecked
                val httpClient = if (isRetrofitSelected) {
                    HttpClient.RETROFIT
                } else if (isKtorSelected) {
                    HttpClient.KTOR
                } else {
                    return@setOnClickListener
                }
                makeRequest(httpClient)
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