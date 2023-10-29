package com.marcossan.scanner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Size
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.marcossan.scanner.ui.theme.ScannerTheme


class MainActivity_old : ComponentActivity() {

//    private lateinit var barcodeView: DecoratedBarcodeView
//
//    private val requestPermission =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//            if (isGranted) {
//                barcodeView.resume()
//            }
//        }
//
//    private val text = MutableLiveData("")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val beepManager = BeepManager(this)

//        val root = layoutInflater.inflate(R.layout.layout, null)
//        barcodeView = root.findViewById(R.id.barcode_scanner)


//        val formats = listOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)
//        barcodeView.barcodeView.decoderFactory = DefaultDecoderFactory(formats)
//        barcodeView.initializeFromIntent(intent)
//        val callback = object : BarcodeCallback {
//            override fun barcodeResult(result: BarcodeResult) {
//                if (result.text == null || result.text == text.value) {
//                    return
//                }
//                text.value = result.text
//                beepManager.playBeepSoundAndVibrate()
//            }
//        }
//        barcodeView.decodeContinuous(callback)

//        setContent {
//
//
////            val state = text.observeAsState()
////            state.value?.let {
////                ZxingDemo(it)
////            }
//
////            val navController = rememberNavController()
//
//
//            ScannerTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
////                    Button(onClick = {
////                        navController.navigate("add_product")
////                    }) {
////                        Text(text = "Añadir producto")
////                    }
//
//                    var code by remember {
//                        mutableStateOf("")
//                    }
//                    val context = LocalContext.current
//                    val lifecycleOwner = LocalLifecycleOwner.current
//                    val cameraProviderFeature = remember {
//                        ProcessCameraProvider.getInstance(context)
//                    }
//                    var hasCameraPermission by remember {
//                        mutableStateOf(
//                            ContextCompat.checkSelfPermission(
//                                context,
//                                Manifest.permission.CAMERA
//                            ) == PackageManager.PERMISSION_GRANTED
//                        )
//                    }
//                    val launcher = rememberLauncherForActivityResult(
//                        contract = ActivityResultContracts.RequestPermission(),
//                        onResult = { granted ->
//                            hasCameraPermission = granted
//                        }
//                    )
////                    val options = ScanOptions()
////                    options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
////                    options.setPrompt("Scan a barcode")
////                    options.setCameraId(0) // Use a specific camera of the device
////                    options.setBeepEnabled(false)
////                    options.setBarcodeImageEnabled(true)
////                    options.setOrientationLocked(true)
//
//                    LaunchedEffect(key1 = true) {
//                        launcher.launch(Manifest.permission.CAMERA)
//                    }
//                    Column(modifier = Modifier.fillMaxSize()) {
//                        if (hasCameraPermission) {
//                            AndroidView(
//                                factory = { context ->
//                                    val previewView = PreviewView(context)
//                                    val preview = Preview.Builder().build()
//                                    val selector = CameraSelector.Builder()
//                                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//                                        .build()
//                                    preview.setSurfaceProvider(previewView.surfaceProvider)
//
//                                    val imageAnalysis = ImageAnalysis.Builder()
//                                        .setTargetResolution(
//                                            Size(// TODO: Revistar import
//                                                previewView.width,
//                                                previewView.height
//                                            )
//                                        )
//                                        .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
//                                        .build()
//                                    imageAnalysis.setAnalyzer(
//                                        ContextCompat.getMainExecutor(context),
//                                        QrCodeAnalyzer { result ->
//                                            code = result
//                                        }
//                                    )
//                                    try {
//                                        cameraProviderFeature.get().bindToLifecycle(
//                                            lifecycleOwner,
//                                            selector,
//                                            preview,
//                                            imageAnalysis
//                                        )
//                                    } catch (e: Exception) {
//                                        e.printStackTrace()
//                                    }
//                                    previewView
//                                },
//                                modifier = Modifier.weight(1f)
//                            )
//                            Text(
//                                text = code,
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.Bold,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(32.dp)
//                            )
//                        }
//
//                    }
//                }
//            }
//        }
    }

//    override fun onResume() {
//        super.onResume()
//        requestPermission.launch(android.Manifest.permission.CAMERA)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        barcodeView.pause()
//    }
}

//@Composable
//fun ZxingDemo(root: View, value: String) {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.TopCenter
//    ) {
//        AndroidView(modifier = Modifier.fillMaxSize(),
//            factory = {
//                root
//            })
//        if (value.isNotBlank()) {
//            Text(
//                modifier = Modifier.padding(16.dp),
//                text = value,
//                color = Color.White,
//                //style = MaterialTheme.typography.h4
//            )
//        }
//    }
//}
