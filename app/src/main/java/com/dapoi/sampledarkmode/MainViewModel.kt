package com.dapoi.sampledarkmode

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class MainViewModel(settingDataStore: SettingDataStore) : ViewModel() {
    val getDarkMode: LiveData<UITheme> = settingDataStore.getDarkMode.asLiveData()
}