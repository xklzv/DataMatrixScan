package com.example.datamatrixscan

import android.app.Application
import com.example.datamatrixscan.data.AppDatabase
import com.example.datamatrixscan.data.ScanRepository


class App : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { ScanRepository(database.scanRecordDao()) }
}