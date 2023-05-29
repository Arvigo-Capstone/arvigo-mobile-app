package id.arvigo.arvigobasecore.ui.feature.personality

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.arvigo.arvigobasecore.data.source.network.response.personality.PersonalityDataItem
import id.arvigo.arvigobasecore.data.source.network.response.personality.PersonalityResponse
import id.arvigo.arvigobasecore.ui.component.PrimaryButton
import id.arvigo.arvigobasecore.ui.navigation.Screen
import org.koin.androidx.compose.getViewModel

@Composable
fun PersonalityMainTestScreen(
   navController: NavController,
) {
    PersonalityMainTestContent(
        navController = navController,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalityMainTestContent(
    navController: NavController,
) {
    val viewModel: PersonalityViewModel = getViewModel()
    val state by viewModel.state.collectAsState()
    Scaffold() {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3.8f)
            ) {
                if(state.isEmpty()) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(align = Alignment.Center)
                        )
                    }
                }
                items(viewModel.state.value.size) {data ->
                    val dataResult = state[data]
                    PersonalTestCard(
                        data = dataResult.question,
                    )
                }
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f)
                    .padding(horizontal = 15.dp, vertical = 4.dp)
            ) {
                PrimaryButton(title = "Continue", onClick = {
                    navController.navigate(Screen.PersonalityResult.route)
                } )
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalTestCard(
    data: String,
) {

    var state by remember {
        mutableStateOf(false)
    }

    val radioOptions = listOf("1", "2", "3", "4", "5")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[2] ) }

    Surface() {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = data, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.padding(top = 12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Strongly\nDisagree", style = MaterialTheme.typography.titleMedium)
                radioOptions.forEach { text ->
                    Box(
                        Modifier
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = {
                                    onOptionSelected(text)
                                }
                            )
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) }
                        )
                    }
                }
                Text(text = "Strongly\nAgree", style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.padding(top = 16.dp))
            Divider()
        }
    }
}

@Preview
@Composable
fun PersonalTestCardPrev() {
    PersonalTestCard(
        data = "I am the life of the party.",
    )
}