package com.example.s2m.android.view.sendMoneyScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.s2m.android.util.Routes
import com.example.s2m.android.view.AutoSlidingCarousel
import com.example.s2m.android.view.WalletCard
import com.example.s2m.model.User
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.LogoutViewModel
import com.example.s2m.viewmodel.SendMoneyViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SendMoneyScreen1(
    loginViewModel: LoginViewModel = viewModel(),
    sendMoneyViewModel: SendMoneyViewModel = viewModel(),
    navController: NavController, logoutViewModel: LogoutViewModel = viewModel()
){

    val amount:String          by sendMoneyViewModel.amount.collectAsState()
    val memo :String           by sendMoneyViewModel.memo.collectAsState()
    val beneficiaryName:String by sendMoneyViewModel.beneficiaryName.collectAsState()
    val identityNumber:String  by sendMoneyViewModel.identityNumber.collectAsState()
    val notify:Boolean         by sendMoneyViewModel.notify.collectAsState()
    val toPhone:String         by sendMoneyViewModel.toPhone.collectAsState()
    val user: User             by loginViewModel.user.collectAsState()
    val scaffoldState          = rememberScaffoldState()
    val coroutineScope         = rememberCoroutineScope()
    val context                = LocalContext.current

   // var isTextFieldEmpty by remember { mutableStateOf(false) }

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
                                            text = "Send Money", modifier = Modifier
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
                                            Text(
                                                text = "58",
                                                fontSize = 12.sp,
                                                color = Color.Red,
                                                modifier = Modifier.padding(start = 4.dp)
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
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
            ) {
            Spacer(modifier = Modifier.height(1.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "From",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color= Color.Black,
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
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "To",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            }

            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black, // Change the text color here
                    cursorColor = Color.Blue, // Change the cursor color here
                    focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                    unfocusedIndicatorColor = Color.Transparent // Change the unfocused border color here
                ),
                value = beneficiaryName,
                onValueChange = { sendMoneyViewModel.onBeneficiaryNameChanged(it)},
                label = { Text("Beneficiary Name*", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .border(
                        border = BorderStroke(2.dp, color = Color.Black),
                        shape = RoundedCornerShape(20)
                    ),
                shape = RoundedCornerShape(20.dp)
            )
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black, // Change the text color here
                    cursorColor = Color.Blue, // Change the cursor color here
                    focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                    unfocusedIndicatorColor = Color.Transparent // Change the unfocused border color here
                ),
                value = identityNumber,
                onValueChange = { sendMoneyViewModel.onIdentityNumberChanged(it)},
                label = { Text("Identity Number*", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .border(
                        border = BorderStroke(2.dp, color = Color.Black),
                        shape = RoundedCornerShape(20)
                    ),
                shape = RoundedCornerShape(20.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().height(60.dp)) {
                CustomToggleButton(selected = notify) {
                    sendMoneyViewModel.onNotifyChanged(it)
                }
                Text(
                    text = "Notify beneficiary by sms",
                    modifier = Modifier.padding(start = 8.dp),
                    color=Color.DarkGray
                )
            }


            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black, // Change the text color here
                    cursorColor = Color.Blue, // Change the cursor color here
                    focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                    unfocusedIndicatorColor = Color.Transparent // Change the unfocused border color here
                ),
                value = toPhone,
                onValueChange = { sendMoneyViewModel.ontoPhoneChanged(it)},
                label = { Text("Beneficiary Phone*", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
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
                value = amount,
                onValueChange = { sendMoneyViewModel.onAmountChanged(it)},
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
           /* fun onConfirmButtonClick() {
                if (memo.isEmpty()) {
                    isTextFieldEmpty = true
                } else {
                    isTextFieldEmpty = false
                    // Perform other actions or validations here
                }
            }*/
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black, // Change the text color here
                    cursorColor = Color.Blue, // Change the cursor color here
                    focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                    unfocusedIndicatorColor = Color.Transparent // Change the unfocused border color here
                ),
                value = memo,
                onValueChange = { sendMoneyViewModel.onMemoChanged(it)},
                label = { Text("Memo*", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .border(
                        border = BorderStroke(2.dp, color =  Color.Black),
                        shape = RoundedCornerShape(20)
                    ),
                shape = RoundedCornerShape(20.dp)
            )
            Box(
             modifier=Modifier.height(120.dp)
            ){
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff00E0F7)),
                shape = RoundedCornerShape(50),
                onClick = { navController.navigate(Routes.Send2.name)  },
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

        }
    }
}

@Composable
fun CheckCircle(
    modifier: Modifier = Modifier
) {

    Card(
        shape = CircleShape, modifier = modifier.size(20.dp), elevation = 0.dp
    ) {
        Box(modifier = Modifier.background(Color.White))
    }

}

@Composable
fun CustomToggleButton(
    selected: Boolean,
    modifier: Modifier = Modifier,
    onUpdate: (Boolean) -> Unit
) {

    Card(
        modifier = modifier
            .width(50.dp)
            .clickable {
                onUpdate(!selected)
            }, shape = RoundedCornerShape(16.dp), elevation = 0.dp
    ) {
        Box(
            modifier = Modifier.background(
                if (selected) Color(0xff00E0F7) else Color.Black
            ), contentAlignment = if (selected) Alignment.TopEnd else Alignment.TopStart
        ) {
            CheckCircle(modifier = Modifier.padding(5.dp))
        }
    }

}


