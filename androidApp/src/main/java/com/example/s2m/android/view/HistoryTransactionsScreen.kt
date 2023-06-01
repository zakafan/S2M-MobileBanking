package com.example.s2m.android.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.text.Selection.setSelection
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.util.toAndroidPair
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.util.BottomNav
import com.example.s2m.android.util.DrawerContent
import com.example.s2m.model.User
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.LogoutViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HistoryTransactionsScreen(
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavController,
    logoutViewModel: LogoutViewModel = viewModel()
){
    val user: User by loginViewModel.user.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()




    Scaffold(

        backgroundColor = Color(0xffE5FBFE),
        topBar = {
            Surface(

                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            ) {
                TopAppBar(
                    backgroundColor = Color(0xff00E0F7),
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
                                                tint = Color.Black,
                                                modifier = Modifier.padding(end = 25.dp)
                                            )
                                        }

                                        Text(
                                            text = "Transaction History", modifier = Modifier
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
                                                tint = Color.Black
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
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
        ) {
            MyContent("From:")
            MyContent("To    :")
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff00E0F7)),
                shape = RoundedCornerShape(50),
                onClick = {

                },
                modifier = Modifier
                    .width(500.dp)
                    .height(65.dp)
                    .padding(top=20.dp)
            ) {
                Text(
                    text = "Search",
                    fontSize = 20.sp,
                    color = Color.White,
                )
            }
        }


    }
}

@Composable
fun MyContent(text:String){

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )




        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp)
                .border(width = 1.dp, color = Color.Black, shape = RectangleShape),


            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$text ${mDate.value}",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    color = Color.Black
                )
                IconButton(
                    onClick = {
                        mDatePickerDialog.show()
                    },
                    modifier = Modifier.padding(start = 25.dp)
                ) {
                    Icon(
                        Icons.Filled.DateRange,
                        contentDescription = "Menu",
                        tint = Color.Black
                    )
                }
            }
        }



}

