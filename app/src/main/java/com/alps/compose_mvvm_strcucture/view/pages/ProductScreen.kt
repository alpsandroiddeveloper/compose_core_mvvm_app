import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alps.compose_mvvm_strcucture.utils.Resource
import com.alps.compose_mvvm_strcucture.view.components.MainToolbar
import com.alps.compose_mvvm_strcucture.view.components.ProductView
import com.alps.compose_mvvm_strcucture.viewmodel.AppViewModel

@Composable
fun ProductScreen(
    navController: NavController,
    appViewModel: AppViewModel = hiltViewModel()
) {
    // Collects the list of products (or loading/error state) from the ViewModel as StateFlow
    val productResource by appViewModel.product.collectAsState()

    // LaunchedEffect is triggered when the screen is first composed or the key (Unit) changes
    LaunchedEffect(Unit) {
        appViewModel.fetchProduct()  // Fetches the list of products from the ViewModel when the screen loads
    }
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            MainToolbar("Product List",navController)

            when (val resource = productResource) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(50.dp)
                                .padding(10.dp)
                        )
                    }

                }

                is Resource.Success -> {
                    ProductView(resource.data, navController)
                }

                is Resource.Error -> {
                    Text(
                        text = "Error: ${resource.message}"
                    )
                }
            }
        }
    }
}
