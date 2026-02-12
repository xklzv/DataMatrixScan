package com.example.datamatrixscan.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.datamatrixscan.data.ScanRecord
import com.example.datamatrixscan.data.ScanRepository

class HistoryViewModel(private val repository: ScanRepository) : ViewModel() {
    val allRecords: LiveData<List<ScanRecord>> = repository.getAllScans().asLiveData()
}