package com.example.s2m.android.view

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
import com.example.s2m.viewmodel.ContactUsViewModel
import com.google.relay.compose.BoxScopeInstanceImpl.align

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ContactUsScreen(
    navController: NavController,
    contactUsViewModel: ContactUsViewModel = viewModel()
){

    val subject     : String     by contactUsViewModel.subject.collectAsState()
    val message     : String     by contactUsViewModel.message.collectAsState()
    val phoneNumber : String     by contactUsViewModel.phoneNumber.collectAsState()
    val fullName    : String     by contactUsViewModel.fullName.collectAsState()
    val email       : String     by contactUsViewModel.email.collectAsState()
    val requestType : Int        by contactUsViewModel.requestType.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Select a request") }
    val requestTypeList: List<String> = listOf("Account problem","Account termination","Change request",
        "Other complaints","Problem related to fees","Problem related to the application",
        "Request for assistance","Transactional problem")
    val context = LocalContext.current

    Scaffold (
        topBar = {
            TopAppBar(
                backgroundColor = Color.Black,
                modifier = Modifier.height(80.dp),
                title = {

                    Column(
                        modifier = Modifier.align(Alignment.TopCenter)
                    ) {
                        Row(
                            modifier = Modifier.padding(bottom = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {navController.popBackStack()

                                },
                                modifier = Modifier.padding(bottom = 50.dp)
                            ) {
                                Icon(
                                    Icons.Filled.ArrowBack,
                                    contentDescription = "Menu",
                                    tint = Color(0xff00E0F7),
                                    //  modifier = Modifier.padding(end = 1.dp)
                                )
                            }

                            Text(
                                text = "Contact Us", modifier = Modifier
                                    .weight(1f)
                                    .padding(bottom = 50.dp),
                                color= Color.White
                            )

                        }
                    }

                })
        },
        backgroundColor = Color.LightGray,

    ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .width(500.dp)
                    .background(color = Color.DarkGray, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center,
            ) {
                TextButton(
                    onClick = { showDialog = true },
                    modifier = Modifier.padding(vertical = 3.dp)
                ) {
                    Text(
                        text = selectedOption,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black, // Change the text color here
                    cursorColor = Color.Blue, // Change the cursor color here
                    focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                    unfocusedIndicatorColor = Color.Transparent // Change the unfocused border color here
                ),
                value = subject,
                onValueChange = { contactUsViewModel.onSubjectChanged(it)},
                label = { Text("Subject*", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .border(
                        border = BorderStroke(2.dp, color = Color.Black),
                        shape = RoundedCornerShape(20)
                    ),
                shape = RoundedCornerShape(20.dp),

            )

            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black, // Change the text color here
                    cursorColor = Color.Blue, // Change the cursor color here
                    focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                    unfocusedIndicatorColor = Color.Transparent // Change the unfocused border color here
                ),
                value = fullName,
                onValueChange = { contactUsViewModel.onFullNameChanged(it)},
                label = { Text("Full name*", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .border(
                        border = BorderStroke(2.dp, color = Color.Black),
                        shape = RoundedCornerShape(20)
                    ),
                shape = RoundedCornerShape(20.dp),

            )
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black, // Change the text color here
                    cursorColor = Color.Blue, // Change the cursor color here
                    focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                    unfocusedIndicatorColor = Color.Transparent // Change the unfocused border color here
                ),
                value = phoneNumber,
                onValueChange = { contactUsViewModel.onPhoneNumberChanged(it)},
                label = { Text("Phone number*", color = Color.Black) },
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
                value = email,
                onValueChange = { contactUsViewModel.onEmailChanged(it)},
                label = { Text("Email*", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .border(
                        border = BorderStroke(2.dp, color = Color.Black),
                        shape = RoundedCornerShape(20)
                    ),
                shape = RoundedCornerShape(20.dp),

            )
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black, // Change the text color here
                    cursorColor = Color.Blue, // Change the cursor color here
                    focusedIndicatorColor = Color.Transparent, // Change the focused border color here
                    unfocusedIndicatorColor = Color.Transparent // Change the unfocused border color here
                ),
                value = message,
                onValueChange = { contactUsViewModel.onMessageChanged(it)},
                label = { Text("Message*", color = Color.Black, modifier= Modifier.padding(top=45.dp)) },
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
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
                          if(contactUsViewModel.contactUs(subject,email,requestType,message,fullName,phoneNumber)
                              == ContactUsViewModel.ContactUsState.Success){
                              contactUsViewModel.clearState()
                              Toast.makeText(context,"Request has been sent successfully",Toast.LENGTH_SHORT).show()
                          }
                },
                modifier = Modifier
                    .width(500.dp)
                    .height(45.dp)
            ) {
                Text(
                    text = "Send",
                    fontSize = 20.sp,
                    color = Color.White,
                )
            }

            if (showDialog) {
                AlertDialog(
                    backgroundColor = Color.White,
                    onDismissRequest = { showDialog = false },
                    title = {
                        Text(text = "Select your request", color = Color.Black, fontWeight = FontWeight.SemiBold)
                    },
                    buttons = {
                        Column {
                            requestTypeList.forEach { requesttype ->
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(
                                            0xff00E0F7
                                        )
                                    ),
                                    onClick = {
                                        selectedOption = requesttype
                                       // contactUsViewModel.onRequestTypeChanged(beneficiary.mobilePhone)

                                        showDialog = false

                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Text(text = requesttype, color = Color.Black)
                                }

                            }
                        }
                    }
                )
            }


        }

    }

}