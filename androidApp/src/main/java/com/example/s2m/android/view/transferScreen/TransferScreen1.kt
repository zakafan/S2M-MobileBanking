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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.util.BottomNav
import com.example.s2m.android.util.DrawerContent
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
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var showDialog2 by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Select beneficiary") }
   // val options = listOf("Option 1", "Option 2", "Option 3")
    val beneficiaryList = user.responseLogin?.beneficiaryList


    val context = LocalContext.current



    Scaffold(

        backgroundColor = Color.LightGray,
        topBar = {
            Surface(

                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            ) {
                TopAppBar(
                    backgroundColor = Color.Black,
                    modifier = Modifier.height(150.dp),
                    title = {
                        user.responseLogin?.let {

                            Box(modifier = Modifier.fillMaxSize()) {
                                Column(
                                    modifier = Modifier.align(Alignment.TopCenter)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(bottom = 55.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IconButton(
                                            onClick = {
                                                coroutineScope.launch {
                                                    scaffoldState.drawerState.open()
                                                }
                                            },
                                            modifier = Modifier.padding(bottom = 50.dp)
                                        ) {
                                            Icon(
                                                Icons.Filled.Menu,
                                                contentDescription = "Menu",
                                                tint = Color(0xff00E0F7),
                                                modifier = Modifier.padding(end = 25.dp)
                                            )
                                        }

                                        Text(
                                            text = "Transfer", modifier = Modifier
                                                .weight(1f)
                                                .padding(bottom = 50.dp),
                                            color= Color.White
                                        )

                                        IconButton(
                                            onClick = { /*TODO*/ },
                                            modifier = Modifier.padding(bottom = 50.dp)
                                        ) {
                                            Icon(
                                                Icons.Filled.Notifications,
                                                contentDescription = "notification",
                                                tint = Color(0xff00E0F7)
                                            )
                                        }
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Balance: ${it.equipmentList[0].balance} MAD", color = Color.White,modifier= Modifier.padding(top=80.dp,end=70.dp), fontSize = 25.sp, fontStyle = FontStyle.Italic)
                                }
                            }
                        }
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
    ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            
        ) {
            Spacer(modifier = Modifier.height(1.dp))
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
                Card(
                    modifier = Modifier.padding(1.dp),
                    shape = RoundedCornerShape(18.dp),

                    ) {
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
                }}
            Spacer(modifier = Modifier.height(5.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "To",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    )
            }
            Box(
                modifier = Modifier
                    .width(500.dp)
                    .background(color = Color.Black, shape = RoundedCornerShape(20.dp)),
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
                    unfocusedIndicatorColor = Color.Transparent // Change the unfocused border color here
                ),
                value = amount,
                onValueChange = { transferViewModel.onAmountChanged(it)},
                label = { Text("Amount*", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .border(
                        border = BorderStroke(2.dp, color = Color.Black),
                        shape = RoundedCornerShape(20)
                    ),
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black, // Change the text color here
                    cursorColor = Color.Blue, // Change the cursor color here
                    focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                    unfocusedIndicatorColor = Color.Transparent // Change the unfocused border color here
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
                shape = RoundedCornerShape(20.dp)
            )

            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff00E0F7)),
                shape = RoundedCornerShape(50),
                onClick = {
                   if(selectedOption=="Select beneficiary"){
                       Toast.makeText(context,"Please choose a beneficiary!!",Toast.LENGTH_SHORT).show()
                   }else if(memo =="" || amount==""){
                       showDialog2 = true
                       Toast.makeText(context,"Fill the necessary fields!! ",Toast.LENGTH_SHORT).show()
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

                    onDismissRequest = { showDialog = false },
                    title = {
                        Text(text = "Choose a beneficiary")
                    },
                    buttons = {
                        Column {
                           beneficiaryList?.forEach { beneficiary ->
                               Button(
                                   colors = ButtonDefaults.buttonColors(
                                       backgroundColor = Color(
                                           0xff00E0F7
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
                                       .padding(vertical = 8.dp)
                               ) {
                                   Text(text = beneficiary.name, color = Color.Black)
                               }

                           }
                            Button(
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow),
                                shape = RoundedCornerShape(50),
                                onClick = {
                                    Toast.makeText(context, "Still in development", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier
                                    .padding(start=85.dp)


                            ) {

                                Text(
                                    text = "Add beneficiary",
                                    color = Color.Black,
                                )
                            }
                        }
                    }
                )
            }
            if (showDialog2) {
                AlertDialog(
                    modifier = Modifier.height(150.dp).width(300.dp),

                    onDismissRequest = { showDialog2 = false },
                    title = {
                        Text(text = "Fill the necessary fields",modifier=Modifier.padding(start=40.dp,top=20.dp))
                    },
                    buttons = {
                        Column {

                            Button(
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                                shape = RoundedCornerShape(50),
                                onClick = {
                                    showDialog2 = false

                                },
                                modifier = Modifier
                                    .padding(start=50.dp,top=60.dp).width(200.dp)


                            ) {

                                Text(
                                    text = "OK",
                                    color = Color.Black,
                                )
                            }
                        }
                    }
                )
            }
        }
        }
    }


