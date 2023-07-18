package com.example.s2m.android.view.transferScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import android.os.Build
import android.view.View
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.R
import com.example.s2m.android.util.*
import com.example.s2m.android.view.sendMoneyScreen.captureScreenshot
import com.example.s2m.model.User
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.LogoutViewModel
import com.example.s2m.viewmodel.TransferViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TransferScreen3(
    transferViewModel: TransferViewModel = viewModel(),
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    logoutViewModel: LogoutViewModel = viewModel(),

){

    val user: User by loginViewModel.user.collectAsState()
    val currentDateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    val formattedDateTime = currentDateTime.format(formatter)

    val context = LocalContext.current
    val screenshotBitmap = captureScreenshot(context)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Handle the result if needed
    }

    val captureAndShareScreenshot = {

        // Save the screenshot to a temporary file
        val screenshotFile = File(context.cacheDir, "screenshot.png")
        val outputStream = FileOutputStream(screenshotFile)
        screenshotBitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()

        // Create an intent to share the screenshot
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        val uri = FileProvider.getUriForFile(context, context.packageName + ".fileprovider", screenshotFile)
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)

        // Launch the activity to choose an application
        val chooserIntent = Intent.createChooser(shareIntent, "Share via")
        val packageManager = context.packageManager
        val activities = packageManager.queryIntentActivities(chooserIntent, PackageManager.MATCH_DEFAULT_ONLY)
        val targetIntents = activities.map { intent ->
            intent.activityInfo.packageName
        }.toTypedArray()

        launcher.launch(chooserIntent.apply {
            putExtra(Intent.EXTRA_INITIAL_INTENTS, targetIntents)
        })
    }

    Scaffold(
        backgroundColor = Color(backgroundColor),
        topBar = {
            Surface(

                modifier = Modifier.fillMaxWidth(),

            ) {
                TopAppBar(
                    backgroundColor = Color(topBarColor),
                    title = {
                        Text(text = "Transfer", color = Color.White)
                    }
                )
            }
        },
        drawerContent = {
            DrawerContent(user = user, loginViewModel = loginViewModel, navController = navController, logoutViewModel =logoutViewModel )
        },
        bottomBar = {
            BottomNav(navController = navController,"beneficiary")
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Icon(
                    modifier = Modifier.size(150.dp),
                    painter = painterResource(id = R.drawable.checked),
                    contentDescription = "Success",
                    tint = Color(topBarColor)
                    )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Successful operation",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                color = Color.Gray,
                thickness = 2.dp,
                modifier = Modifier
                    .height(2.dp)
                    .width(320.dp)
                    .padding(start = 40.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                backgroundColor = Color(0xffAFD3E2),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("From       : ${user.responseLogin?.phone} ", color = Color.Black)
                    Text(
                        "To            : ${transferViewModel.toPhone.value} ",
                        color = Color.Black
                    )
                    Text("Amount  : ${transferViewModel.amount.value} MAD", color = Color.Black)
                    Text("Memo     : ${transferViewModel.memo.value}", color = Color.Black)
                    Text("Fee          : 12.00 MAD", color = Color.Black)
                    Text("Date        : $formattedDateTime ", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .width(500.dp)
                .height(50.dp),
            contentAlignment = Alignment.TopCenter,
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(topBarColor)),
                shape = RoundedCornerShape(50),
                onClick = {
                    transferViewModel.clearState()
                    navController.navigate(Routes.Welcome.name)
                },
                modifier = Modifier
                    .width(500.dp)
                    .height(45.dp)
            ) {
                Text(
                    text = "Confirm",
                    fontSize = 20.sp,
                    color = Color.White,
                )
            }
        }
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .width(500.dp)
                    .height(120.dp),
                contentAlignment = Alignment.TopCenter,
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(topBarColor)),
                    shape = RoundedCornerShape(50),
                    onClick = {
                      // captureAndShareScreenshot()
                    },
                    modifier = Modifier
                        .width(500.dp)
                        .height(45.dp)
                ) {
                    Text(
                        text = "Share",
                        fontSize = 20.sp,
                        color = Color.White,
                    )
                }
            }
    }
    }
}

fun captureScreenshot(context: Context): Bitmap? {
    val view = View(context)
    view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    val screenshot = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(screenshot)
    view.draw(canvas)
    return screenshot
}