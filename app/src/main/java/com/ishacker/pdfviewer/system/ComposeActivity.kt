package com.ishacker.pdfviewer.system

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ishacker.pdfviewer.system.PdfRendererView
import com.ishacker.pdfviewer.system.compose.PdfRendererViewCompose
import com.ishacker.pdfviewer.system.util.PdfSource
import com.ishacker.pdfviewer.system.ui.theme.AndroidpdfviewerTheme
import java.io.File

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidpdfviewerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyPdfScreenFromUrl(
                        modifier = Modifier.systemBarsPadding(),
                        url = "https://source.android.com/docs/compatibility/5.0/android-5.0-cdd.pdf"
                    )
                }
            }
        }
    }
}

@Composable
private fun MyPdfScreenFromUrl(url: String, modifier: Modifier = Modifier) {
    PdfRendererViewCompose(
        source = remember(url) { PdfSource.Remote(url) },
        modifier = modifier,
        jumpToPage = 4,
        statusCallBack = remember {
            object : PdfRendererView.StatusCallBack {
                override fun onPdfLoadStart() {
                    Log.i("statusCallBack", "onPdfLoadStart")
                }

                override fun onPdfLoadProgress(
                    progress: Int,
                    downloadedBytes: Long,
                    totalBytes: Long?
                ) {
                    Log.i("statusCallBack", "onPdfLoadProgress: $progress")
                }

                override fun onPdfLoadSuccess(absolutePath: String) {
                    Log.i("statusCallBack", "onPdfLoadSuccess: $absolutePath")
                }

                override fun onError(error: Throwable) {
                    Log.e("statusCallBack", "onError: ${error.message}")
                }

                override fun onPageChanged(currentPage: Int, totalPage: Int) {
                    Log.i("statusCallBack", "onPageChanged: $currentPage / $totalPage")
                }

                override fun onPdfRenderStart() {
                    Log.i("statusCallBack", "onPdfRenderStart")
                }

                override fun onPdfRenderSuccess() {
                    Log.i("statusCallBack", "onPdfRenderSuccess")
                }
            }
        },
        zoomListener = remember {
            object : PdfRendererView.ZoomListener {
                override fun onZoomChanged(isZoomedIn: Boolean, scale: Float) {
                    Log.i("PDF Zoom", "Zoomed in: $isZoomedIn, Scale: $scale")
                }
            }
        }
    )
}

@Composable
private fun MyPdfScreenFromUri(modifier: Modifier = Modifier) {
    val (uri, setUri) = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        setUri(it)
    }

    Column(modifier = modifier) {
        AnimatedContent(
            targetState = uri,
            label = "",
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { selectedUri ->
            selectedUri?.let {
                PdfRendererViewCompose(
                    source = PdfSource.LocalUri(it),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Button(
            onClick = { launcher.launch(arrayOf("application/pdf")) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Pick PDF File")
        }
    }
}

@Composable
private fun MyPdfScreenFromAsset(modifier: Modifier = Modifier) {
    PdfRendererViewCompose(
        source = PdfSource.PdfSourceFromAsset("quote.pdf"),
        modifier = modifier
    )
}

@Composable
private fun MyPdfScreenFromFile() {
    val pdfFile = File("path/to/your/file.pdf") // Replace with actual path
    PdfRendererViewCompose(
        source = PdfSource.LocalFile(pdfFile)
    )
}

@Preview(showBackground = true)
@Composable
private fun MyPdfScreenFromUrlPreview() {
    AndroidpdfviewerTheme {
        MyPdfScreenFromUrl("https://css4.pub/2015/textbook/somatosensory.pdf")
    }
}
