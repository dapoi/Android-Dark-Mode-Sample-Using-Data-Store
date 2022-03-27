package com.dapoi.sampledarkmode

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dapoi.sampledarkmode.UITheme.DARK
import com.dapoi.sampledarkmode.UITheme.LIGHT
import com.dapoi.sampledarkmode.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var settingDataStore: SettingDataStore
    private lateinit var viewModel: DarkModeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingDataStore = SettingDataStore(this)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(settingDataStore)
        )[DarkModeViewModel::class.java]
        viewModel.getDarkMode.observe(this) {
            checkThemeMode(it)
        }

        binding.btnMove.setOnClickListener {
            Intent(this@MainActivity, DetailActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun checkThemeMode(uiTheme: UITheme?) {
        when (uiTheme) {
            LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            null -> TODO()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}