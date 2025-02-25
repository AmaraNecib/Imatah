package com.example.imatah
//TP
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.imatah.model.Category
import com.example.imatah.model.Report
import com.example.imatah.ui.theme.ImatahTheme
import com.example.imatah.view.MyScreen
import com.example.imatah.viewmodel.CategoryViewModel
import com.example.imatah.viewmodel.ReportViewModel
import okhttp3.internal.wait

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImatahTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Greeting()
                }
            }
        }
    }
}







@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting() {
    var buttomState by remember {
        mutableStateOf( "Home")
    }
    val categoryViewModel = viewModel<CategoryViewModel>()
    val reportViewModel = viewModel<ReportViewModel>()
    Scaffold(






        content = {


            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when (buttomState) {
                    "Home" -> MyScreen(categoryViewModel, reportViewModel)  // عرض MyScreen عند اختيار Home
                    "Account" -> Text(text = "Account", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                    "Map" -> Text(text = "Map", fontSize = 25.sp, fontWeight = FontWeight.Bold)

                    "Search" -> Text(text = "Search", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                    "Ouick-Report" -> Text(text = "Ouick-Report", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                    else -> Text(text = "Unknown", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                }
            }
        },
        topBar = {

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Transparent)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))         ){

                TopAppBar(
                    title = {Box( modifier = Modifier.fillMaxWidth().padding(end = 10.dp),Alignment.TopEnd){
                        Text(text = "IMATAH") }
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "icon",
                                tint = Color.White,
                                modifier = Modifier

                                    .background(Color(0xFFFFC107), shape = MaterialTheme.shapes.extraLarge) // شكل مدور للأيقونة
                                    .padding(6.dp)
                            )
                        }
                    },

                    actions = {
                        AsyncImage(
                            model = "https://picsum.photos/id/240/600/400",
                            contentDescription =null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(50.dp)
                                .fillMaxHeight()

                                .clip(RoundedCornerShape(50.dp))
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White,
                        actionIconContentColor = Color.White,
                        navigationIconContentColor = Color.White

                    ),



                    )
            }
        },
        bottomBar = {
            Surface(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                    .shadow(8.dp, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(24.dp)),
                color = Color.Gray
            ){
                NavigationBar (
                    containerColor = Color.White,

                    ){

                    NavigationBarItem(
                        selected = buttomState == "Home",
                        onClick = { buttomState = "Home" },
                        label = { Text(text = "Home", color = Color.Black)  },
                        icon = { Icon(imageVector = Icons.Default.Home , contentDescription = null , tint = Color.Black) }

                    )
                    NavigationBarItem(
                        selected = buttomState == "map",
                        onClick = {  },
                        label = { Text(text = "Map",color = Color.Black) },
                        icon = { Icon(imageVector = Icons.Default.LocationOn , contentDescription = null, tint = Color.Black) }

                    )
                    NavigationBarItem(
                        selected = buttomState == "Search",
                        onClick = { },
                        label = { Text(text = "Search",color = Color.Black) },
                        icon = { Icon(imageVector = Icons.Default.Search , contentDescription = null, tint = Color.Black) }

                    )
                    NavigationBarItem(
                        selected = buttomState == "Ouick Report",
                        onClick = {  },
                        label = { Text(text = "Ouick-Report", maxLines = 1,color = Color.Black) },
                        icon = { Icon(im
                                ageVector = Icons.Default.DateRange , contentDescription = null, tint = Color.Black) }

                    )
                    NavigationBarItem(
                        selected = buttomState == "Account",
                        onClick = {  },
                        label = { Text(text = "Account",color = Color.Black) },
                        icon = { Icon(imageVector = Icons.Default.AccountCircle , contentDescription = null, tint = Color.Black) }

                    )


                }
            }


        }
    )

}