package com.example.s2m.android.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.android.animation.SegmentType
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.s2m.android.R
import com.example.s2m.android.util.BottomNav
import com.example.s2m.android.util.DrawerContent
import com.example.s2m.android.util.backgroundColor
import com.example.s2m.android.util.topBarColor
import com.example.s2m.model.User
import com.example.s2m.viewmodel.ContactUsViewModel
import com.example.s2m.viewmodel.LoginViewModel
import com.example.s2m.viewmodel.LogoutViewModel
import com.example.s2m.viewmodel.WalletStatsViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.horizontal.topAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.axis.formatter.PercentageFormatAxisValueFormatter
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HistoryTransactionsScreen(
    loginViewModel: LoginViewModel = viewModel(),
    navController: NavController,
    logoutViewModel: LogoutViewModel = viewModel(),
    walletStatsViewModel: WalletStatsViewModel = viewModel()
) {


        val data = listOf("2022-07-01" to 20f, "2022-07-02" to 60f, "2022-07-04" to 40f,"2022-07-05" to 50f).associate { (dateString, yValue) ->
            LocalDate.parse(dateString) to yValue
        }
        val data1 = listOf(5f to 20f , 8f to 60f, 10f to 40f)
        val cartentry = entryModelOf(1 to 20f , 8f to 80f, 10f to 40f)
        val chartEntryModel1 = entryModelOf(4f, 12f, 8f, 16f,20f)

        val xValuesToDates = data.keys.associateBy { it.toEpochDay().toFloat() }
        val chartEntryModel = xValuesToDates.keys.zip(data.values) { x, y -> entryOf(x, y) }.let { entryModelOf(it) }
        val horizontalAxisValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
            (xValuesToDates[value] ?: LocalDate.ofEpochDay(value.toLong())).format(DateTimeFormatter.ofPattern("dd MMM "))
        }
        Scaffold (
            topBar = {
                TopAppBar(
                    backgroundColor = Color(topBarColor),
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
                                    contentDescription = "Back",
                                    tint = Color.White,
                                ) }

                            Text(
                                text = "Contact Us", modifier = Modifier
                                    .weight(1f),
                                color=Color.White
                            ) }
                    })
            },
            backgroundColor = Color(backgroundColor),

            ){

            walletStatsViewModel.getWalletStats()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Spacer(modifier = Modifier.height(50.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Chart(
                        chart = lineChart(),
                        model = chartEntryModel1,
                        startAxis = startAxis(),
                        bottomAxis = bottomAxis(valueFormatter = horizontalAxisValueFormatter),
                        modifier = Modifier.size(400.dp)


                    )
                }

        }



    }

}




@SuppressLint("SuspiciousIndentation")
@Composable
fun MyContent(text:String) {

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
            mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
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

