package com.example.datamatrixscan.data

import kotlinx.coroutines.flow.Flow
import java.util.Date

class ScanRepository(private val dao: ScanRecordDao) {
    suspend fun insertScan(code: String): Boolean {
        val record = ScanRecord(code, Date())
        val result = dao.insert(record)
        return result != 0L
    }

    fun getAllScans(): Flow<List<ScanRecord>> = dao.getAllRecords()

    suspend fun isCodeExists(code: String): Boolean = dao.isCodeExists(code)
}