package com.mgado.expensetracker.views.home.ocr_scanner

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.mgado.expensetracker.R
import com.mgado.expensetracker.components.SubViewBaseWidget

@Composable
fun OcrScannerView(navController: NavHostController) {
    var scannedText by remember { mutableStateOf("Point camera at text") }
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }
    var isScanning by remember { mutableStateOf(true) }

    if (permissionGranted) {
        SubViewBaseWidget(navController = navController) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isScanning){
                    AndroidView(factory = { ctx ->
                        PreviewView(ctx).apply {
                            this.scaleType = PreviewView.ScaleType.FILL_CENTER
                            startCamera(this, context) { result ->
                                if (result.isNotEmpty() && isScanning) {
                                    isScanning = false
                                    scannedText = result
                                }
                            }
                        }
                                          },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(2.dp, Color.Black, RoundedCornerShape(16.dp)))
                } else {
                    Button(onClick = { isScanning = true },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.green))) {
                        Text("Restart Scanning", style = MaterialTheme.typography.labelMedium)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(scannedText,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                        .verticalScroll(rememberScrollState()))
                }
            }
        }
    } else {
        CameraPermissionRequest { permissionGranted = true }
    }
}

@SuppressLint("UnsafeOptInUsageError")
fun startCamera(previewView: PreviewView, context: Context, onTextRecognized: (String) -> Unit) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().also {
            it.surfaceProvider = previewView.surfaceProvider
        }
        val imageAnalyzer = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                    processImageProxy(imageProxy, onTextRecognized)
                }
            }
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(context as LifecycleOwner, cameraSelector, preview, imageAnalyzer)
    }, ContextCompat.getMainExecutor(context))
}

@OptIn(ExperimentalGetImage::class)
fun processImageProxy(imageProxy: ImageProxy, onTextRecognized: (String) -> Unit) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image)
            .addOnSuccessListener { result ->
                val recognizedText = result.text
                onTextRecognized(recognizedText)
                if(recognizedText.isNotEmpty()){ imageProxy.close() }
            }
            .addOnFailureListener { Log.e("OCR", "Failed: ${it.message}") }
            .addOnCompleteListener { imageProxy.close() }

    } else {
        imageProxy.close()
    }
}

@Composable
fun CameraPermissionRequest(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current
    val cameraPermission = Manifest.permission.CAMERA
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            Toast.makeText(context, "Camera permission is required", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, cameraPermission) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(cameraPermission)
        } else {
            onPermissionGranted()
        }
    }
}

