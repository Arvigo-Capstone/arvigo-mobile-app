package id.arvigo.arvigobasecore.data.repository

import android.util.Log
import androidx.compose.runtime.saveable.rememberSaveable
import id.arvigo.arvigobasecore.data.source.local.AuthPreferences
import id.arvigo.arvigobasecore.data.source.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductDetailRepository(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences,
) {
    fun getProductDetail(id: String) = flow {
        val token = authPreferences.getAuthToken()
        Log.d("Hit API Detail Product", "Get Product Detail with id $id")
        emit(apiService.getProductDetail(
            token = "Bearer $token",
            id = id,
        ).data)
    }.flowOn(Dispatchers.IO)

    fun getProductStore(id: String) = flow {
        val token = authPreferences.getAuthToken()
        Log.d("Hit API Product Store", "Get Product Store with id $id")
        emit(apiService.getProductStore(
            token = "Bearer $token",
            id = id,
        ).data.marketplaces)
    }.flowOn(Dispatchers.IO)

}