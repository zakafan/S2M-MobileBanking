package com.example.s2m.android.view.transferScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.util.*
import com.example.s2m.android.view.*
import com.example.s2m.model.User
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.LogoutViewModel
import com.example.s2m.viewmodel.TransferViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TransferScreen1(
    loginViewModel: LoginViewModel = viewModel(),
    transferViewModel: TransferViewModel = viewModel(),
    navController: NavController,
    logoutViewModel: LogoutViewModel = viewModel()

){

    val amount:String   by transferViewModel.amount.collectAsState()
    val memo :String    by transferViewModel.memo.collectAsState()
    val user: User by loginViewModel.user.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var showDialog2 by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Select beneficiary") }
    val beneficiaryList = user.responseLogin?.beneficiaryList


    val context = LocalContext.current



    Scaffold(

        backgroundColor = Color(backgroundColor),
        topBar = {
            TopAppBar(
                backgroundColor = Color(0xff112D4E),
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
                                tint = Color.White,
                            ) }

                        Text(
                            text = "Transfer", modifier = Modifier
                                .weight(1f),
                            color=Color.White
                        ) }
                })
        },

        drawerContent = {
            DrawerContent(user = user, loginViewModel = loginViewModel, navController = navController, logoutViewModel =logoutViewModel )
        },
        bottomBar = {
            BottomNav(navController = navController,"beneficiary")
        }
    ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "From",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color=Color.Black,
                )
            }
           user.responseLogin?.let {
                val wallets = it.equipmentList

                    AutoSlidingCarousel(
                        itemsCount = wallets.size,
                        itemContent = { index ->
                            WalletCard(
                                balance = wallets[index].balance,
                                currency = wallets[index].currencyAlphaCode,
                                cardNumber = "879",
                                modifier = Modifier
                                    .padding(30.dp)
                                    .fillMaxSize()

                            )
                        }
                    )
                }
            Spacer(modifier = Modifier.height(30.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "To",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = Modifier
                    .width(500.dp)
                    .background(color = Color(0xff112D4E), shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center,
            )
            {
                TextButton(
                    onClick = { showDialog = true },
                    modifier = Modifier.padding(vertical = 3.dp)
                ) {
                    Text(text = selectedOption,color=Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black, // Change the text color here
                    cursorColor = Color.Blue, // Change the cursor color here
                    focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color(backgroundColor)
                ),
                value = amount,
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it != ',' }
                    transferViewModel.onAmountChanged(filteredValue)
                },
                label = { Text("Amount*", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .border(
                        border = BorderStroke(2.dp, color = Color.Black),
                        shape = RoundedCornerShape(20)
                    ),
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )

            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black, // Change the text color here
                    cursorColor = Color.Blue, // Change the cursor color here
                    focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                    unfocusedIndicatorColor = Color.Transparent,
                    backgroundColor = Color(backgroundColor)

                ),
                value = memo,
                onValueChange = { transferViewModel.onMemoChanged(it)},
                label = { Text("Memo*", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .border(
                        border = BorderStroke(2.dp, color = Color.Black),
                        shape = RoundedCornerShape(20)
                    ),
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff112D4E)),
                shape = RoundedCornerShape(50),
                onClick = {
                   if(selectedOption=="Select beneficiary"){
                       Toast.makeText(context,"Please choose a beneficiary!!",Toast.LENGTH_SHORT).show()
                   }else if(memo =="" || amount==""){
                       showDialog2 = true

                   }  else {
                       navController.navigate("transfer2")
                   }
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
            if (showDialog) {
                AlertDialog(
                    backgroundColor= Color(backgroundColor),
                    onDismissRequest = { showDialog = false },
                    title = {
                        Text(text = "Choose a beneficiary",
                            fontWeight = FontWeight.Bold)
                    },
                    buttons = {
                        Column {
                           beneficiaryList?.forEach { beneficiary ->
                               Button(
                                   colors = ButtonDefaults.buttonColors(
                                       backgroundColor = Color(
                                           0xffAFD3E2
                                       )
                                   ),
                                   onClick = {
                                       selectedOption = beneficiary.name
                                       transferViewModel.ontoPhoneChanged(beneficiary.mobilePhone)
                                       transferViewModel.onBeneficiaryNameChanged(beneficiary.name)
                                       showDialog = false

                                   },
                                   modifier = Modifier
                                       .fillMaxWidth()
                                       .padding(vertical = 2.dp)
                               ) {
                                   Text(text = beneficiary.name, color = Color.Black)
                               }

                           }
                            Button(
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(
                                    topBarColor)),
                                shape = RoundedCornerShape(50),
                                onClick = {
                                    navController.navigate(Routes.Beneficiary.name)
                                },
                                modifier = Modifier
                                    .padding(start=85.dp)


                            ) {

                                Text(
                                    text = "Add beneficiary",
                                    color = Color.White,
                                )
                            }
                        }
                    }
                )
            }
            if (showDialog2) {
                MyAlertDialog(text = "Fill the necessary fields", show =showDialog2 ) {
                    showDialog2 = false
                }
            }
        }
        }
    }


