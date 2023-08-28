package com.example.s2m.android.view.withdrawalScreen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.s2m.android.util.QrCodeAnalyzer
import com.example.s2m.android.util.Routes
import com.example.s2m.android.util.parseString
import com.example.s2m.viewmodel.MerchantPaymentViewModel
import com.example.s2m.viewmodel.WithdrawalViewModel

@Composable
fun Scanner(
    navController: NavController,
    withdrawalViewModel: WithdrawalViewModel = viewModel()
){
    var code by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission() ,
        onResult ={granted ->
            hasCameraPermission = granted
        }
    )
    LaunchedEffect(key1 = true ){
        launcher.launch(Manifest.permission.CAMERA)
    }
    Column (
        modifier = Modifier.fillMaxSize()
    ){

        if(hasCameraPermission){
            AndroidView(
                factory = {context ->
                    val previewView = PreviewView(context)
                    val preview = Preview.Builder().build()
                    val selector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context),
                        QrCodeAnalyzer{result ->
                            code =result
                            val  res= parseString(code)
                            if(res!=null){
                                val firstValue = res.first
                                val secondValue = res.second
                                withdrawalViewModel.ontoPhoneChanged(firstValue )
                                withdrawalViewModel.onAmountChanged(secondValue)
                                navController.navigate(Routes.Withdrawal1.name) {
                                    popUpTo(Routes.Welcome.name) {
                                        inclusive = true
                                        withdrawalViewModel.navigateBackToMain()
                                    }
                                }

                            }
                        }
                    )
                    try {
                        cameraProviderFuture.get().bindToLifecycle(
                            lifecycleOwner,
                            selector,
                            preview,
                            imageAnalysis
                        )
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                    previewView

                },
                modifier = Modifier.weight(1f)
            )
            Text(
                text = code,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            )
        }

    }
}