package com.example.datamatrixscan.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "scan_records")
data class ScanRecord(
    @PrimaryKey
    val code: String,
    val timestamp: Date
)