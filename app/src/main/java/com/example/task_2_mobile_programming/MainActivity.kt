package com.example.task_2_mobile_programming

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task_2_mobile_programming.ui.theme.Task2MobileProgrammingTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentFormScreen()
        }
    }
}

@Preview
@Composable
fun StudentFormScreen(){
    var nameState by remember { mutableStateOf("") }

    var emailState by remember {mutableStateOf("")}
    var dateState by remember { mutableStateOf("") }

    var selectedOption by remember {mutableStateOf("")}
    var isAgreed by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    //გამოყენებული ფერთა პალიტრა: https://colorhunt.co/palette/1b262c0f4c753282b8bbe1fa
    val backgroundColor = Color(0xFF1B262C)
    val primaryColor = Color(0xFF0F4C75)
    val secondaryColor = Color(0xFF3282B8)
    val textColor = Color(0xFFBBE1FA)

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            //რადგან თვის ინდექსი 0-დან იწყება month + 1 საჭიროა, რომ მაგალითად მარტი "მე-2" თვე არ იყოს
            //"%02d/%02d/%04d" ფორმატი იმისთვის, რომ თარიღი 5/24/2026 მაგივრად გამოჩნდეს, როგორც 05/24/2026
            dateState = formattedDate
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Student Form",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = secondaryColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "შეავსეთ თქვენი ინფორმაცია",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = secondaryColor,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        //OutlinedTextField-ის ფერები
        val textFieldColors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = secondaryColor,
            unfocusedBorderColor = primaryColor,

            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            cursorColor = secondaryColor,

            focusedLabelColor = secondaryColor,
            unfocusedLabelColor = Color.Gray,
        )

        OutlinedTextField(
            value = nameState,
            onValueChange = { nameState = it },
            label = { Text("სახელი და გვარი") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = textFieldColors,
            singleLine = true
        )

        OutlinedTextField(
            value = emailState,
            onValueChange = { emailState = it },
            label = { Text("ელ-ფოსტა") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = textFieldColors,
            singleLine = true
        )


        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = dateState,
                onValueChange = { },
                label = { Text("აირჩიეთ თარიღი") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(0.dp),
                colors = textFieldColors,
                readOnly = true,
                enabled = true
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { datePickerDialog.show() }
            )
        }

        HorizontalDivider(color = primaryColor, thickness = 4.dp) //ვიზუალური გამყოფი

            Text(text = "თქვენი ფავორიტი მიმართულება:", color = textColor, fontSize = 16.sp)
            val options = listOf("Android", "iOS", "Web", "Networks", "Databases")
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                options.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedOption = option }
                            .background(primaryColor, RoundedCornerShape(50.dp))
                            .padding(6.dp)
                    ) {
                        RadioButton(
                            selected = (selectedOption == option),
                            onClick = { selectedOption = option },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = secondaryColor,
                                unselectedColor = textColor
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = option, color = textColor)
                    }
                }
            }

        HorizontalDivider(color = primaryColor, thickness = 4.dp) //ვიზუალური გამყოფი



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryColor, RoundedCornerShape(50.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "ვეთანხმები წესებს და პირობებს",
                    color = textColor,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    checked = isAgreed,
                    onCheckedChange = { isAgreed = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = textColor,
                        checkedTrackColor = secondaryColor,

                        uncheckedThumbColor = textColor,
                        uncheckedTrackColor = primaryColor
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp)) //უჩინარი გამყოფი

            Button(
                onClick = {
                    val isAllTextFieldsFilled = nameState.isNotBlank() &&
                            emailState.isNotBlank() &&
                            emailState.contains("@") && //იმეილი @-ის გარეშე არ არსებობს ამიტომ დავამატე ამის ვალიდაცია
                            dateState.isNotBlank()

                    val isRadioSelected = selectedOption.isNotBlank()

                    if (isAllTextFieldsFilled && isRadioSelected && isAgreed) {
                        Toast.makeText(context, "მონაცემები გაიგზავნა!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "შეავსეთ ყველა ველი!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = secondaryColor,
                    contentColor = primaryColor
                )
            ) {
                Text(text = "Submit", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }