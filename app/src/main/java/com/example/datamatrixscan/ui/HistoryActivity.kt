package com.example.datamatrixscan.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datamatrixscan.App
import com.example.datamatrixscan.R

class HistoryActivity : AppCompatActivity() {

    private lateinit var viewModel: HistoryViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ScanRecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val repository = (application as App).repository
        val factory = HistoryViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        observeData()
    }

    private fun observeData() {
        viewModel.allRecords.observe(this) { records ->
            adapter = ScanRecordAdapter(records)
            recyclerView.adapter = adapter
        }
    }
}