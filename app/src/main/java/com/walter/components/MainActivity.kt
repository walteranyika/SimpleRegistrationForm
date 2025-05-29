package com.walter.components

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.walter.components.ui.theme.ComponentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComponentsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegistrationForm()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationForm() {
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val eduLevels = listOf("High School", "University", "College")
    var selectedEduLevel by remember { mutableStateOf(eduLevels[0]) }

    var showErrors by remember { mutableStateOf(false) }

    val isFirstNameValid = firstname.isNotBlank()
    val isLastNameValid = lastname.isNotBlank()

    val isFormValid = isFirstNameValid && isLastNameValid


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Register", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = firstname,
            onValueChange = { firstname },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )

        if (showErrors && !isFirstNameValid) {
            Text("First name is required", color = Color.Red, fontSize = 12.sp)
        }

        OutlinedTextField(
            value = lastname,
            onValueChange = { lastname },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Text("Gender", fontWeight = FontWeight.Medium)

        Row {
            listOf("Male", "Female").forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    RadioButton(selected = gender == option, onClick = { gender = option })
                    Text(option)
                }
            }
        }


        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded != expanded }) {
            OutlinedTextField(
                value = selectedEduLevel,
                onValueChange = {},
                readOnly = true,
                label = { Text("Education Level") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = true)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }) {
                eduLevels.forEach { level ->
                    DropdownMenuItem(
                        text = { Text(level) },
                        onClick = {
                            selectedEduLevel = level
                            expanded = false
                        }
                    )

                }
            }
        }

        Button(
            onClick = {
                showErrors = true
                if (isFormValid) {
                    // Submit logic here
                    Log.d("SUCCESS", "RegistrationForm: ")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Submit", fontSize = 16.sp)
        }

    }

}

@Preview(showBackground = true)
@Composable
fun FormPreview() {
    RegistrationForm()
}