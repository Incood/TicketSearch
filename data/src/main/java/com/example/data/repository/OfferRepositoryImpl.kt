import android.util.Log
import com.example.data.network.RetrofitClient
import com.example.domain.model.Offer
import com.example.domain.repository.OfferRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse


class OfferRepositoryImpl : OfferRepository {
    override suspend fun getOffers(): List<Offer> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.instance.getOffers().awaitResponse()
                Log.d("OfferRepositoryImpl", "Request URL: ${response.raw().request.url}")
                if (response.isSuccessful) {
                    Log.d("OfferRepositoryImpl", "Response successful: ${response.body()}")
                    response.body()?.offers ?: emptyList()
                } else {
                    val errorBody = response.errorBody()?.string() ?: "No error message"
                    Log.e("OfferRepositoryImpl", "Response error: $errorBody, Code: ${response.code()}")
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e("OfferRepositoryImpl", "Exception: ${e.message}")
                emptyList()
            }
        }
    }
}