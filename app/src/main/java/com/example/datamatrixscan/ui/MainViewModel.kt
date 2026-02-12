package com.example.datamatrixscan.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datamatrixscan.data.ScanRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ScanRepository) : ViewModel() {

    private val _scannedCode = MutableLiveData<String?>()
    val scannedCode: LiveData<String?> = _scannedCode

    private val _showActionButtons = MutableLiveData(false)
    val showActionButtons: LiveData<Boolean> = _showActionButtons

    private val _messageEvent = MutableLiveData<String?>()
    val messageEvent: LiveData<String?> = _messageEvent

    fun setScannedCode(code: String) {
        _scannedCode.value = code
        _showActionButtons.value = true
    }

    fun clearScannedCode() {
        _scannedCode.value = null
        _showActionButtons.value = false
    }

    fun saveCurrentCode() {
        val code = _scannedCode.value ?: return
        viewModelScope.launch {
            val success = repository.insertScan(code)
            if (success) {
                _messageEvent.value = "Код сохранён"
                clearScannedCode()
            } else {
                _messageEvent.value = "Такой код уже есть в базе"
            }
        }
    }

    fun onMessageShown() {
        _messageEvent.value = null
    }
}