package com.example.viewmodelsandapitutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.viewmodelsandapitutorial.api.Todo
import com.example.viewmodelsandapitutorial.ui.theme.ViewModelsAndApiTutorialTheme
import com.example.viewmodelsandapitutorial.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //get the view model before on create
        val vm = TodoViewModel()
        super.onCreate(savedInstanceState)
        setContent {
            ViewModelsAndApiTutorialTheme {
                TodoView(vm)
            }
        }
    }
}

@Composable
fun TodoView(vm: TodoViewModel) {
    // To call suspend function safely inside composeable, use LaunchedEffectComposable
    LaunchedEffect(key1 = Unit, block = {
        vm.getTodoList()
    })
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text(text = "Todos")
                    }
                })
        },
        content = {
            if (vm.errorMessage.isEmpty()){
                Column(modifier = Modifier.padding(0.dp)) {
                    LazyColumn(modifier = Modifier.fillMaxHeight()){
                        items(vm.todoList){todo ->
                            Column{
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    CardView(
                                        itemName = todo.name, 
                                        price = todo.price, 
                                        sold = todo.sold, 
                                        seller = todo.seller, 
                                        negotiable = todo.negotiable, 
                                        condition=todo.condition)
                                }
                            }
                        }
                    }
                }
            }
            else{
                Text(text = vm.errorMessage)
            }
        }
    )
}

@Composable
fun CardView(itemName: String,
             price: Float,
             sold: Boolean,
             seller: String,
             negotiable: Boolean,
             condition: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { },
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.on_sale_image),
                contentDescription = "On sell item",
                modifier = Modifier
                    .size(100.dp)
                    .padding(20.dp)
            )
            Text(
                buildAnnotatedString {
                    //append("Item name: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                    ) {
                        append(itemName)
                    }
                }
            )
            Text(
                buildAnnotatedString {

                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
                        append(price.toString())
                    }
                    append(" USD")
                }
            )

            Text(
                buildAnnotatedString {
                    //append("Seller: ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                    ) {
                        append(seller)
                    }
                }
            )

            if (negotiable){
                //append("Negotiable")
                //Text(text = "Negotiable", color = Color.Green)
                Text(
                    buildAnnotatedString {
                        //append("Seller: ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.DarkGray)
                        ) {
                            append("Negotiable")
                        }
                    }
                )
            }
            else {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold, color = Color.LightGray)
                        ) {
                            append("Non-negotiable")
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ViewModelsAndApiTutorialTheme {
        val vm = TodoViewModel()
        TodoView(vm = vm)
    }
}