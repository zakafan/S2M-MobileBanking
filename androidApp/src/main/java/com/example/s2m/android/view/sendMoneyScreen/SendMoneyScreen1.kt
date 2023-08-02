package com.example.s2m.android.view.sendMoneyScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.s2m.model.User
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.LogoutViewModel
import com.example.s2m.viewmodel.SendMoneyViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
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
    var showDialog by remember { mutableStateOf(false) }


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
                            text = "Money Send", modifier = Modifier
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
                    unfocusedIndicatorColor = Color.Transparent ,
                    backgroundColor = Color(backgroundColor)
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
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions(
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
                value = identityNumber,
                onValueChange = {  if (it.length <= 11)sendMoneyViewModel.onIdentityNumberChanged(it)},
                label = { Text("Identity Number*", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
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
                    unfocusedIndicatorColor = Color.Transparent ,
                    backgroundColor = Color(backgroundColor)
                ),
                value = toPhone,
                onValueChange = {  if (it.length <= 9)sendMoneyViewModel.ontoPhoneChanged(it)},
                label = { Text("Beneficiary Phone*", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .border(
                        border = BorderStroke(2.dp, color = Color.Black),
                        shape = RoundedCornerShape(20)
                    ),
                shape = RoundedCornerShape(20.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next)
            )

            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black, // Change the text color here
                    cursorColor = Color.Blue, // Change the cursor color here
                    focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                    unfocusedIndicatorColor = Color.Transparent ,
                    backgroundColor = Color(backgroundColor)
                ),
                value = amount,
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it != ',' }
                    sendMoneyViewModel.onAmountChanged(filteredValue)
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
                    unfocusedIndicatorColor = Color.Transparent ,
                    backgroundColor = Color(backgroundColor)
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
                shape = RoundedCornerShape(20.dp),
                        keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                        )
            )
            Box(
             modifier=Modifier.height(120.dp)
            ){
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(topBarColor)),
                shape = RoundedCornerShape(50),
                onClick = {
                    if(beneficiaryName=="" || identityNumber=="" || toPhone ==""|| amount=="" || memo==""){
                        showDialog = true

                    }else{
                        navController.navigate(Routes.Send2.name)
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
            }
            if(showDialog){
                AlertDialog(
                    modifier = Modifier.height(150.dp).width(300.dp),
                    backgroundColor = Color(backgroundColor),

                    onDismissRequest = { showDialog = false },
                    title = {
                        Text(
                            text = "Fill the necessary fields",
                            modifier=Modifier.padding(start=40.dp,top=20.dp),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    buttons = {
                        Column {
                            Button(
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(topBarColor)),
                                shape = RoundedCornerShape(50),
                                onClick = {
                                    showDialog = false
                                },
                                modifier = Modifier
                                    .padding(start=50.dp,top=60.dp).width(200.dp)


                            ) {

                                Text(
                                    text = "OK",
                                    color = Color.White,
                                )
                            }
                        }
                    }
                )
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
                if (selected) Color(topBarColor) else Color.DarkGray
            ), contentAlignment = if (selected) Alignment.TopEnd else Alignment.TopStart
        ) {
            CheckCircle(modifier = Modifier.padding(5.dp))
        }
    }

}


