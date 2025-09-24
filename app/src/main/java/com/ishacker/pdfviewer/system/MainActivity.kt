package com.ishacker.pdfviewer.system

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.ishacker.pdfviewer.system.databinding.ActivityMainBinding
import com.rajat.pdfviewer.PdfRendererView
import com.rajat.pdfviewer.PdfViewerActivity
import com.rajat.pdfviewer.util.saveTo
import java.io.File


class MainActivity : AppCompatActivity() {

    // View Binding
    private lateinit var binding: ActivityMainBinding

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
     * Sets up the PDF status listener for monitoring PDF rendering progress.
     */
    private fun setupPdfStatusListener() {
        binding.pdfView.statusListener = object : PdfRendererView.StatusCallBack {
            override fun onPdfLoadStart() {
                Log.i("PDF Status", "Loading started")
            }

            override fun onPdfLoadProgress(progress: Int, downloadedBytes: Long, totalBytes: Long?) {
                Log.i("PDF Status", "Download progress: $progress%")
            }

            override fun onPdfLoadSuccess(absolutePath: String) {
                Log.i("PDF Status", "Load successful: $absolutePath")
            }

            override fun onError(error: Throwable) {
                Log.e("PDF Status", "Error loading PDF: ${error.message}")
            }

            override fun onPageChanged(currentPage: Int, totalPage: Int) {
                Log.i("PDF Status", "Page changed: $currentPage / $totalPage")
            }

            override fun onPdfRenderStart() {
                Log.d("PDF Status", "Render started")
            }

            override fun onPdfRenderSuccess() {
                Log.d("PDF Status", "Render successful")
                binding.pdfView.jumpToPage(2)
            }
        }

        binding.pdfView.zoomListener = object : PdfRendererView.ZoomListener {
            override fun onZoomChanged(isZoomedIn: Boolean, scale: Float) {
                Log.i("PDF Zoom", "Zoomed in: $isZoomedIn, Scale: $scale")
            }
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
    private fun launchPdfFromUri(uri: Uri) {
        val fileName = File(uri.path).name
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