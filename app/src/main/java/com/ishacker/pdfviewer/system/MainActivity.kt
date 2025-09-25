package com.ishacker.pdfviewer.system

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.ishacker.pdfviewer.system.databinding.ActivityMainBinding
import com.rajat.pdfviewer.PdfViewerActivity
import com.rajat.pdfviewer.util.saveTo
import java.io.File


class MainActivity : AppCompatActivity() {

    // View Binding
    private lateinit var binding: ActivityMainBinding
    private val pdf = PDF()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Apply edge-to-edge layout
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Inflate layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Default ActionBar title
        supportActionBar?.title = "PDF Viewer"

        // Setup Click Listeners
        setupListeners()
        // Open PDF from file manager
        pdf.uri = intent.data
        if (pdf.uri != null) {
            launchPdfFromUri(pdf.uri)
        }
    }

    /**
     * Sets up click listeners for the UI buttons.
     */
    private fun setupListeners() {

        binding.pickPdfButton.setOnClickListener {
            launchFilePicker()
        }
    }
    /**
     * Launches a file picker for selecting PDFs.
     */
    private fun launchFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
        }
        filePicker.launch(intent)
    }

    /**
     * Handles the result of the file picker.
     */
    private val filePicker = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let { uri ->
                launchPdfFromUri(uri)
            }
        }
    }

    /**
     * Launches a PDF file from a local URI.
     */
    private fun launchPdfFromUri(uri: Uri?) {
        val fileName = File(uri?.path).name
        startActivity(
            PdfViewerActivity.launchPdfFromPath(
                context = this,
                path = uri.toString(),
                pdfTitle = fileName,
                saveTo = saveTo.ASK_EVERYTIME,
                fromAssets = false
            )
        )
    }
}