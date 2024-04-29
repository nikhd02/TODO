package com.example.todo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoList(viewModel: TodoViewModel){
    val todolist by viewModel.todoList.observeAsState()
    var inputText by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            OutlinedTextField(value = inputText, onValueChange = { newValue: String ->
                inputText = newValue
            })

            Button(onClick = {
                viewModel.addTodo(inputText)
                inputText = ""
            }) {
                Text(text = "Add")
            }
        }

        todolist?.let {
            LazyColumn (
                content = {
                    itemsIndexed(it){index: Int, item: Todo ->
                        TodoItem(item =item, onDelete = {
                            viewModel.deleteTodo(item.id)
                        })
                        //                    Text(text = item.toString())
                    }
                }
            )
        }?: Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "No Item yet",
            fontSize = 16.sp)


    }
}

@Composable
fun  TodoItem(item : Todo,onDelete : ()->Unit){
    Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                .background(androidx.compose.material3.MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = SimpleDateFormat("HH:mm:aa, dd/mm", java.util.Locale.ENGLISH).format(item.createdAt),
                fontSize = 12.sp,
                color = androidx.compose.ui.graphics.Color.Companion.LightGray
            )
            Text(
                fontSize = 20.sp,
                color = androidx.compose.ui.graphics.Color.Companion.White,
                text = item.title)
        }
        androidx.compose.material3.IconButton(onClick = onDelete) {
            androidx.compose.material3.Icon(painter = androidx.compose.ui.res.painterResource(id = R.drawable.baseline_delete_24),
                contentDescription = "Delete",
                tint = androidx.compose.ui.graphics.Color.Companion.White)

        }
    }
}

