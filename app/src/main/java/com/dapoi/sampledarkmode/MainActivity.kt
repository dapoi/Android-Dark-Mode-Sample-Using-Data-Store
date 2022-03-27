package com.dapoi.sampledarkmode

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dapoi.sampledarkmode.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var settingDataStore: SettingDataStore
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingDataStore = SettingDataStore(this)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(settingDataStore)
        )[MainViewModel::class.java]
        viewModel.getDarkMode.observe(this) {
            checkThemeMode(it)
        }

        with(binding) {
            applyModeSwitch.setOnCheckedChangeListener { _, isChecked ->
                lifecycleScope.launch {
                    when (isChecked) {
                        true -> settingDataStore.setDarkMode(UITheme.DARK)
                        false -> settingDataStore.setDarkMode(UITheme.LIGHT)
                    }
                }
            }

            btnMove.setOnClickListener {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }

    private fun checkThemeMode(uiTheme: UITheme?) {
        if (uiTheme == UITheme.LIGHT) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.applyModeSwitch.isChecked = false
        } else if (uiTheme == UITheme.DARK) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.applyModeSwitch.isChecked = true
        }
    }
}