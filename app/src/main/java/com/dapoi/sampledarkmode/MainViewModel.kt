package com.dapoi.sampledarkmode

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

class MainViewModel(settingDataStore: SettingDataStore) : ViewModel() {
    val setDataStore: LiveData<UIMode> = settingDataStore.uiModeFlow.asLiveData()
}