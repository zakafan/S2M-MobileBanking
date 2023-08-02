package com.example.s2m.android.view.beneficiaryScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.util.BottomNav
import com.example.s2m.android.util.Routes
import com.example.s2m.android.util.backgroundColor
import com.example.s2m.android.util.topBarColor
import com.example.s2m.util.AddBeneficiaryErrorType
import com.example.s2m.viewmodel.BeneficiaryViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RecapAddBeneficiaryScreen(
    navController: NavController,
    beneficiaryViewModel: BeneficiaryViewModel = viewModel()
){

    val name:String     by beneficiaryViewModel.beneficiaryName.collectAsState()
    val phone:String    by beneficiaryViewModel.phone.collectAsState()
    val context = LocalContext.current

    Scaffold(
        backgroundColor = Color(backgroundColor),
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                ) {
                TopAppBar(
                    backgroundColor = Color(topBarColor),
                    title =  {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {navController.popBackStack()

                                },

                                ) {
                                Icon(
                                    Icons.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.White,
                                ) }

                            Text(
                                text = "Add beneficiary", modifier = Modifier
                                    .weight(1f),
                                color= Color.White
                            ) }
                    }
                )
            }
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
            Spacer(modifier = Modifier.height(100.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Beneficiary Wallet",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color=Color.Black,
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
                    Text("Name                  : ${beneficiaryViewModel.beneficiaryName.value} ", color = Color.Black)
                    Text("Phone Number  : +212${beneficiaryViewModel.phone.value} ", color = Color.Black)
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
                        if(beneficiaryViewModel.addBeneficiary(name,phone)==BeneficiaryViewModel.AddBeneficiaryState.Success){
                            beneficiaryViewModel.clearState()
                            navController.navigate(Routes.Welcome.name)
                        }else if(beneficiaryViewModel.addBeneficiary(name,phone)==BeneficiaryViewModel.AddBeneficiaryState.Error(AddBeneficiaryErrorType.InvalidPhone)){
                            Toast.makeText(context,"Invalid Phone Number !!",Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .width(500.dp)
                        .height(45.dp)
                ) {
                    Text(
                        text = "Validate",
                        fontSize = 20.sp,
                        color = Color.White,
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
}}

