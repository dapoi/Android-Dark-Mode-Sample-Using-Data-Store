package com.dapoi.sampledarkmode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dapoi.sampledarkmode.databinding.ActivityDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var settingDataStore: SettingDataStore
    private lateinit var viewModel: DarkModeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail"
        settingDataStore = SettingDataStore(this)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(settingDataStore)
        )[DarkModeViewModel::class.java]
        viewModel.getDarkMode.observe(this) {
            checkThemeMode(it)
        }

        with(binding) {
            applyModeSwitch.setOnCheckedChangeListener { _, isChecked ->
                lifecycleScope.launch(Dispatchers.IO) {
                    when (isChecked) {
                        true -> {
                            settingDataStore.setDarkMode(UITheme.DARK)
                        }
                        false -> {
                            settingDataStore.setDarkMode(UITheme.LIGHT)
                        }
                    }
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

    override fun recreate() {
        finish()
        overridePendingTransition(
            androidx.appcompat.R.anim.abc_fade_in,
            androidx.appcompat.R.anim.abc_fade_out
        )
        startActivity(intent)
        overridePendingTransition(
            androidx.appcompat.R.anim.abc_fade_in,
            androidx.appcompat.R.anim.abc_fade_out
        )
    }
}