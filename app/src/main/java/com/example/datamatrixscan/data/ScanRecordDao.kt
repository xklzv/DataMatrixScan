package com.example.datamatrixscan.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanRecordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(record: ScanRecord): Long

    @Query("SELECT * FROM scan_records ORDER BY timestamp DESC")
    fun getAllRecords(): Flow<List<ScanRecord>>

    @Query("SELECT EXISTS(SELECT 1 FROM scan_records WHERE code = :code)")
    suspend fun isCodeExists(code: String): Boolean
}