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

    private val _displayCode = MutableLiveData<String?>()
    val displayCode: LiveData<String?> = _displayCode

    private val _showActionButtons = MutableLiveData(false)
    val showActionButtons: LiveData<Boolean> = _showActionButtons

    private val _messageEvent = MutableLiveData<String?>()
    val messageEvent: LiveData<String?> = _messageEvent

    fun setScannedCode(code: String) {
        _scannedCode.value = code
        _displayCode.value = formatCodeForDisplay(code)  // форматируем
        _showActionButtons.value = true
    }

    fun clearScannedCode() {
        _scannedCode.value = null
        _displayCode.value = null
        _showActionButtons.value = false
    }

    fun saveCurrentCode() {
        val code = _scannedCode.value ?: return
        viewModelScope.launch {
            if (!repository.isCodeExists(code)) {
                repository.insertScan(code)
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

    private fun formatCodeForDisplay(code: String): String {
        return code
            .replace(Char(0x1D), '\n')  // Group Separator
            .replace(Char(0x1E), '\n')  // Record Separator
            .replace(Char(0x1F), '\n')  // Unit Separator
    }
}