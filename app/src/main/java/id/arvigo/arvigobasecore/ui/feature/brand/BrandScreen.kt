package id.arvigo.arvigobasecore.ui.feature.brand

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import id.arvigo.arvigobasecore.R
import id.arvigo.arvigobasecore.data.source.network.response.brands.Brand
import id.arvigo.arvigobasecore.ui.feature.brand.uistate.BrandUiState
import id.arvigo.arvigobasecore.ui.navigation.Screen
import org.koin.androidx.compose.getViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun BrandScreen(
    navController: NavController,
) {
    BrandScreenContent(
        navController = navController,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrandScreenContent(
    navController: NavController,
) {

    val viewModel: BrandViewModel = getViewModel()

    Scaffold(
        modifier = Modifier,
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Merek")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back",
                        )
                    }
                },
            )
        }
    ) {

        val response = viewModel.response.value

        when(response) {
            is BrandUiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(180.dp),
                    state = LazyGridState(),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(it)
                ){
                    items(response.data){ data ->
                        BrandCard(
                            brand = data,
                            onClick = {
                                val encodedUrl = if (data.image == "") {
                                    URLEncoder.encode("https://picsum.photos/200/300/?blur=2", StandardCharsets.UTF_8.toString())
                                } else {
                                    URLEncoder.encode(data.image, StandardCharsets.UTF_8.toString())
                                }
                                navController.navigate( route = Screen.BrandDetail.passData(
                                    data.id,
                                    encodedUrl,
                                    data.name,
                                ))

                            }
                        )
                    }
                }
            }
            is BrandUiState.Failure -> {
                Text(text = response.error.message ?: "Unknown Error")
            }
            BrandUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center)
                )
            }
            BrandUiState.Empty -> {
                Text(text = "Empty Data")
            }
        }

     }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrandCard(
    brand: Brand,
    onClick: () -> Unit,
) {
    val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2)
    Box(
        modifier = Modifier
            .width(itemSize)
            .height(250.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 10.dp)
                .height(300.dp)
                .fillMaxSize()
                .clickable {
                    onClick()
                }
        ) {
            Column() {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(brand.image)
                        .placeholder(R.drawable.img_placeholder)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.img_placeholder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = brand.name,
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun BranPrev() {
}