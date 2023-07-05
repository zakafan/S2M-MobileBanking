package com.example.s2m.android.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.s2m.android.LocationPermissionCallback
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.*
import com.google.relay.compose.BoxScopeInstanceImpl.align
import kotlinx.coroutines.tasks.await

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LocationsScreen(
    navController: NavController,

){

    val context = LocalContext.current
    Scaffold (
        topBar = {
            TopAppBar(
                backgroundColor = Color.Black,
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {navController.popBackStack()

                            },

                            ) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = "Menu",
                                tint = Color(0xff00E0F7),
                            ) }

                        Text(
                            text = "Locations", modifier = Modifier
                                .weight(1f),
                            color=Color.White
                        ) }
                })
        },
        backgroundColor = Color.LightGray,

        ){

        val locationPermissionCallback = LocalContext.current as? LocationPermissionCallback

        // Check if location permission is granted
        val isLocationPermissionGranted = remember {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLocationPermissionGranted.value) {
                Text(text = "Map Screen")
                GoogleMapCompose()
            } else {
                Text(text = "Location permission required")
                Button(onClick = {
                    locationPermissionCallback?.requestLocationPermissions()
                }) {
                    Text(text = "Grant Permission")
                }
            }
        }





    }
}


@Composable
fun GoogleMapCompose() {
    val fusedLocationProvider = rememberFusedLocationState()
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(Unit) {
        val location = fusedLocationProvider.value
        if (location != null) {
            val currentLatLng = LatLng(location.latitude, location.longitude)
            cameraPositionState.position = CameraPosition.fromLatLngZoom(currentLatLng, 10f)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            fusedLocationProvider.value?.let { location ->
                val currentLatLng = LatLng(location.latitude, location.longitude)
                Marker(
                    state = MarkerState(position = currentLatLng),
                    title = "Current Location",
                    snippet = "Marker at current location"
                )
            }
        }

        // Button to go back to current location
        Button(
            onClick = {
                fusedLocationProvider.value?.let { location ->
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(currentLatLng, 10f)
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text(text = "Go to My Location")
        }
    }
}



@Composable
fun rememberFusedLocationState(): MutableState<Location?> {
    val context = LocalContext.current
    val fusedLocationProvider = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    val locationState = remember { mutableStateOf<Location?>(null) }

    LaunchedEffect(Unit) {
        try {
            fusedLocationProvider.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val lastLocation = task.result
                    locationState.value = lastLocation
                } else {
                    // Handle location retrieval failure
                }
            }
        } catch (exception: SecurityException) {
            // Handle location permission denied or other location-related errors
        } catch (exception: Exception) {
            // Handle other exceptions
        }
    }

    return locationState
}




