package com.example.datamatrixscan.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datamatrixscan.data.ScanRecord
import com.example.datamatrixscan.databinding.ItemScanRecordBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ScanRecordAdapter(private val records: List<ScanRecord>) :
    RecyclerView.Adapter<ScanRecordAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemScanRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(record: ScanRecord) {
            binding.tvCode.text = record.code
            val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
            binding.tvTimestamp.text = dateFormat.format(record.timestamp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemScanRecordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(records[position])
    }

    override fun getItemCount() = records.size
}