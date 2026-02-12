package com.example.datamatrixscan.ui



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.datamatrixscan.App
import com.example.datamatrixscan.R
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var tvScannedCode: TextView
    private lateinit var btnScan: Button
    private lateinit var btnHistory: Button
    private lateinit var layoutActions: LinearLayout
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private val scanLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            viewModel.setScannedCode(result.contents)
        } else {
            Toast.makeText(this, "Сканирование отменено", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = (application as App).repository
        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        initViews()
        observeViewModel()
        setupClickListeners()
    }

    private fun initViews() {
        tvScannedCode = findViewById(R.id.tvScannedCode)
        btnScan = findViewById(R.id.btnScan)
        btnHistory = findViewById(R.id.btnHistory)
        layoutActions = findViewById(R.id.layoutActions)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)
    }

    private fun observeViewModel() {
        viewModel.scannedCode.observe(this) { code ->
            if (code != null) {
                tvScannedCode.text = code
                tvScannedCode.visibility = TextView.VISIBLE
                btnScan.visibility = Button.GONE
                layoutActions.visibility = LinearLayout.VISIBLE
            } else {
                tvScannedCode.visibility = TextView.GONE
                btnScan.visibility = Button.VISIBLE
                layoutActions.visibility = LinearLayout.GONE
            }
        }

        viewModel.messageEvent.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.onMessageShown()
            }
        }
    }

    private fun setupClickListeners() {
        btnScan.setOnClickListener {
            val options = ScanOptions()
            options.setBeepEnabled(false)
            options.setOrientationLocked(true)
            scanLauncher.launch(options)
        }

        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        btnSave.setOnClickListener {
            viewModel.saveCurrentCode()
        }

        btnCancel.setOnClickListener {
            viewModel.clearScannedCode()
        }
    }
}