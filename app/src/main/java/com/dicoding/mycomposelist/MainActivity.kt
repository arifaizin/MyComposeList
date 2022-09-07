@file:OptIn(ExperimentalFoundationApi::class)

package com.dicoding.mycomposelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.dicoding.mycomposelist.ui.theme.MyComposeListTheme
import kotlin.random.Random

val contactList = listOf(
    "Arif",
    "Dimas",
    "Fajri",
    "Fikri",
    "Gilang",
    "Hanifa",
    "Ilham",
    "Jesslyn",
    "Rizki",
    "Tia",
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    Contact()
                    ColorBox()
                }
            }
        }
    }
}

@Composable
fun Contact(names: List<String>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        item {
            Text(text = "Header 1", fontWeight = FontWeight.ExtraBold)
        }
        items(names) { name ->
            Text(name)
        }
        item {
            Text(text = "Header 2", fontWeight = FontWeight.ExtraBold)
        }
        itemsIndexed(names) { index, item ->
            Text("Item at index $index is $item")
        }
    }
}

data class Contact(val id: Int, val name: String)

val backgroundColor = listOf(
    0xFFC3DFED,
    0xFF9DF9E2,
    0xFFBFDBFE,
    0xFFA7F3D0,
    0xFFDDD6FE,
    0xFFFDE68A,
    0xFFFECDD3,
    0xFFE4E4E7
)

@Composable
fun ColorBox() {
    val list = remember { mutableStateListOf<Contact>() }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item(span = { GridItemSpan(2) }) {
            Button(onClick = {
                list.add(Contact(Random.nextInt(1000), "new name"))
            }) {
                Text("Add")
            }
        }
        items(list, key = { it.id }) { item ->
            val color by remember {
                mutableStateOf(
                    Color(backgroundColor.random())
//                Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 1f)
                )
            }
            Button(
                onClick = { list.remove(item) },
                colors = ButtonDefaults.buttonColors(backgroundColor = color),
                modifier = Modifier
                    .height(120.dp)
                    .animateItemPlacement(tween(durationMillis = 250))
            ) {
                Text(item.id.toString())
            }
        }
    }
}

@Composable
fun CustomGrid() {
    LazyVerticalGrid(columns = object : GridCells {
        override fun Density.calculateCrossAxisCellSizes(
            availableSize: Int,
            spacing: Int
        ): List<Int> {
            val firstColumn = (availableSize - spacing) * 2 / 3
            val secondColumn = availableSize - spacing - firstColumn
            return listOf(firstColumn, secondColumn)
        }
    }) {

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MyComposeListTheme {
        Contact(names = contactList)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CoursePreview() {
    MyComposeListTheme {
        ColorBox()
    }
}


