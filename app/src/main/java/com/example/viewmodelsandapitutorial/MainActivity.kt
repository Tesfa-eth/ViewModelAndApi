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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.example.viewmodelsandapitutorial.universalcomposables.CardView
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
    var todosState = remember { mutableStateListOf<Todo>() }
    // To call suspend function safely inside composeable, use LaunchedEffectComposable
    LaunchedEffect(key1 = Unit, block = {
        vm.getTodoList()
    })

    var list = listOf<Todo>(
        Todo(1, "Item1name", "Tesfa", "good", 4f, true, true, "www.google.com"),
        Todo(2, "Item1name", "Tesfa", "good", 4f, true, true, "www.google.com")
    )

    
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
                Column(modifier = Modifier.padding(0.dp).fillMaxWidth()) {
                    LazyColumn(modifier = Modifier.fillMaxSize()){
                        items (list){ todo ->
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CardView(
                                    itemName = todo.name,
                                    price = todo.price,
                                    sold = todo.sold,
                                    seller = todo.seller,
                                    negotiable = todo.negotiable,
                                    condition=todo.condition)
                            }

                        }

                        items(vm.todoList){todo ->
                            Column(modifier = Modifier.fillMaxWidth()){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                        //.padding(6.dp),
                                    horizontalArrangement = Arrangement.Center
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
//
//@Composable
//fun OnSaleItemImage(){
//    Column(
//        modifier=Modifier.fillMaxWidth(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Image(painter = painterResource(id = R.drawable.on_sale_image),
//            contentDescription = "On sell item",
//            modifier = Modifier
//                .size(100.dp)
//                .padding(20.dp)
//        )
//    }
//}
//
//@Composable
//fun ItemDetailColumn(itemName: String, price: Float, negotiable: Boolean){
//    Column() {
//        Text(
//            buildAnnotatedString {
//                //append("Item name: ")
//                withStyle(
//                    style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
//                ) {
//                    append(itemName)
//                }
//            }
//        )
//        Text(
//            buildAnnotatedString {
//
//                withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
//                    append(price.toString())
//                }
//                append(" USD")
//            }
//        )
//
//        if (negotiable) {
//            //append("Negotiable")
//            //Text(text = "Negotiable", color = Color.Green)
//            Text(
//                buildAnnotatedString {
//                    //append("Seller: ")
//                    withStyle(
//                        style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.DarkGray)
//                    ) {
//                        append("Negotiable")
//                    }
//                }
//            )
//        } else {
//            Text(
//                buildAnnotatedString {
//                    withStyle(
//                        style = SpanStyle(fontWeight = FontWeight.SemiBold, color = Color.LightGray)
//                    ) {
//                        append("Non-negotiable")
//                    }
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun SellerDetailColumn(seller: String){
//    Column() {
//        Text(
//            buildAnnotatedString {
//                //append("Seller: ")
//                withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
//                ) {
//                    append(seller)
//                }
//            }
//        )
//    }
//}
//
//@Composable
//fun CardView(itemName: String,
//             price: Float,
//             sold: Boolean,
//             seller: String,
//             negotiable: Boolean,
//             condition: String,
//) {
//    Card(
//        modifier = Modifier
//            //.fillMaxWidth()
//            .width(340.dp)
//            .padding(10.dp)
//            .clickable { },
//        elevation = 10.dp
//    ) {
//
//        Column(
//            modifier = Modifier.padding(15.dp),
//            //horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            Row{
//                OnSaleItemImage()
//            }
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceAround
//            ) {
//                ItemDetailColumn(itemName, price, negotiable)
//                SellerDetailColumn(seller)
//            }
//
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ViewModelsAndApiTutorialTheme {
        val vm = TodoViewModel()
        TodoView(vm = vm)
    }
}