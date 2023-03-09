package com.ksd.khincurrency.ui.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ksd.khincurrency.R
import com.ksd.khincurrency.databinding.ActivityBaseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyListActivity : AppCompatActivity() {

    private val binding by lazy { ActivityBaseBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Attach fragment to activity
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CurrencyListFragment())
            .commit()
    }
}