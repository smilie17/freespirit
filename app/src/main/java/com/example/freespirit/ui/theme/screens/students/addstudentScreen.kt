package com.example.freespirit.ui.theme.screens.students

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.freespirit.R
import com.example.freespirit.data.StudentViewModel

@Composable
fun AddstudentScreen(navController: NavController){
    val imageUri = rememberSaveable() { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent())
    {uri: Uri? ->
        uri?.let {imageUri.value=it} }
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var description by  remember { mutableStateOf("") }
    val studentViewModel: StudentViewModel = viewModel()
    val context = LocalContext.current
    Column (modifier = Modifier.fillMaxSize()
        .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        Box(modifier = Modifier.fillMaxWidth()
            .background(Color.Cyan).padding(20.dp)){
            Text(text = "ADD NEW STUDENT",
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Card (shape = CircleShape,
            modifier = Modifier.padding(10.dp).size(200.dp)
        ) {
            AsyncImage(
                model = imageUri.value?:R.drawable.ic_person,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp).clickable {launcher.launch("image/")}
            )
        }
        Text(text = "Upload your Picture")

        OutlinedTextField(
            value = name,
            onValueChange = {newName->name=newName},
            label = { Text(text = "Enter Name") },
            placeholder = { Text(text = "Please enter name") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = "Person Icon") }
        )
        OutlinedTextField(
            value = gender,
            onValueChange = {newGender->gender=newGender},
            label = { Text(text = "Enter Gender") },
            placeholder = { Text(text = "Please enter your gender") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = "Gender Icon") }
        )
        OutlinedTextField(
            value = course,
            onValueChange = {newCourse->course=newCourse},
            label = { Text(text = "Enter Course") },
            placeholder = { Text(text = "Please enter your course") },
            modifier = Modifier.fillMaxWidth(),

        )
        OutlinedTextField(
            value = description,
            onValueChange = {newDescription->description=newDescription},
            label = { Text(text = "Enter Description") },
            placeholder = { Text(text = "Please enter your description") },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            singleLine = false
        )
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,){
            Button(onClick = {}, modifier = Modifier, colors = ButtonDefaults.buttonColors(Color.Black)) { Text(text = "Dashboard") }
            Button(onClick = {
                imageUri.value?.let {
                    studentViewModel.uploadStudentWithImage(it,context,name,gender,course,description)
                }?: Toast.makeText(context,"Please pick an image",Toast.LENGTH_LONG).show()
            }, modifier = Modifier, colors = ButtonDefaults.buttonColors(Color.Green)) { Text(text = "Save") }
        }

    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddstudentScreenPreview(){
    AddstudentScreen(rememberNavController())
}