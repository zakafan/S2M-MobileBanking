package com.example.s2m.android.view.sendMoneyScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.getHash
import com.example.s2m.android.util.BottomNav
import com.example.s2m.android.util.DrawerContent
import com.example.s2m.android.util.Routes
import com.example.s2m.android.view.AutoSlidingCarousel
import com.example.s2m.android.view.WalletCard
import com.example.s2m.model.User
import com.example.s2m.util.TransferErrorType
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.LogoutViewModel
import com.example.s2m.viewmodel.SendMoneyViewModel
import com.example.s2m.viewmodel.TransferViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun SendMoneyScreen2(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    sendMoneyViewModel: SendMoneyViewModel = viewModel(),
    logoutViewModel: LogoutViewModel = viewModel()
){

    val user: User by loginViewModel.user.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var secretCode by remember{ mutableStateOf("") }
    var confirmSecretCode by remember{ mutableStateOf("") }
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
            Box(
                modifier = Modifier
                    .width(500.dp)
                    .height(150.dp)
                    .background(color = Color.Black, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = painterResource(com.example.s2m.android.R.drawable.user),
                        contentDescription = "Person Image",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        colorFilter = ColorFilter.tint(Color(0xff00E0F7))
                    )
                    Text(
                        text = sendMoneyViewModel.beneficiaryName.value,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = sendMoneyViewModel.toPhone.value,
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                backgroundColor = Color.LightGray,
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Amount: ${sendMoneyViewModel.amount.value} MAD", color = Color.Black)
                    Text("Memo: ${sendMoneyViewModel.memo.value}", color = Color.Black)
                    Text("Fee: 12.00 MAD", color = Color.Black)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Please Define A Secret Code",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color= Color.Black,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(
                value =secretCode,
                onValueChange ={
                    if(it.length<=4){
                        secretCode=it
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                decorationBox = {
                    Row(horizontalArrangement = Arrangement.Center){
                        repeat(4){index->
                            val char=when{
                                index >=secretCode.length ->""
                                else ->secretCode[index].toString()
                            }
                            val isFocused=secretCode.length==index
                            Text(
                                modifier = Modifier
                                    .width(40.dp)
                                    .border(
                                        if (isFocused) 4.dp
                                        else 3.dp,
                                        if (isFocused) Color(0xff00E0F7)
                                        else Color.Black,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(2.dp),
                                text=char,
                                style = MaterialTheme.typography.h4,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.width(60.dp))

                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Please Confirm the Secret Code",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color= Color.Black,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(
                value =secretCode,
                onValueChange ={
                    if(it.length<=4){
                        secretCode=it
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                decorationBox = {
                    Row(horizontalArrangement = Arrangement.Center){
                        repeat(4){index->
                            val char=when{
                                index >=secretCode.length ->""
                                else ->secretCode[index].toString()
                            }
                            val isFocused=secretCode.length==index
                            Text(
                                modifier = Modifier
                                    .width(40.dp)
                                    .border(
                                        if (isFocused) 4.dp
                                        else 3.dp,
                                        if (isFocused) Color(0xff00E0F7)
                                        else Color.Black,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(2.dp),
                                text=char,
                                style = MaterialTheme.typography.h4,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.width(60.dp))

                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .width(500.dp)
                    .height(120.dp),
                contentAlignment = Alignment.TopCenter,
            ){
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff00E0F7)),
                    shape = RoundedCornerShape(50),
                    onClick = {
                        sendMoneyViewModel.onSecretCodeChanged(secretCode)
                        navController.navigate(Routes.Send3.name)
                       /* if(sendMoneyViewModel.transfer(transferViewModel.amount.value,transferViewModel.memo.value,transferViewModel.toPhone.value,
                                getHash(transferViewModel.pin.value)
                            )== TransferViewModel.TransferState.Success){
                            Toast.makeText(context,"Transfer successful", Toast.LENGTH_SHORT).show()
                            transferViewModel.clearState()
                        }else if(transferViewModel.transfer(transferViewModel.amount.value,transferViewModel.memo.value,transferViewModel.toPhone.value,
                                getHash(transferViewModel.pin.value)
                            )== TransferViewModel.TransferState.Error(TransferErrorType.MinAmount)){
                            Toast.makeText(context,"The amount is low. Please enter another amount",
                                Toast.LENGTH_SHORT).show()
                        }else if(transferViewModel.transfer(transferViewModel.amount.value,transferViewModel.memo.value,transferViewModel.toPhone.value,
                                getHash(transferViewModel.pin.value)
                            )== TransferViewModel.TransferState.Error(TransferErrorType.PIN)){
                            Toast.makeText(context,"Invalid PIN !!!", Toast.LENGTH_SHORT).show()
                        }else if (transferViewModel.transfer(transferViewModel.amount.value,transferViewModel.memo.value,transferViewModel.toPhone.value,
                                getHash(transferViewModel.pin.value)
                            )== TransferViewModel.TransferState.Error(TransferErrorType.MaxTransactions)){
                            Toast.makeText(context,"the number of maximum transactions is reached !!",
                                Toast.LENGTH_SHORT).show()
                        }*/


                    },
                    modifier = Modifier
                        .width(500.dp)
                        .height(45.dp)) {
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