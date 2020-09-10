package cz.ackee.cookbook.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cz.ackee.cookbook.repository.Repository
import cz.ackee.cookbook.data.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RecipeListModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository = Repository()

    val recipes: MutableLiveData<List<Recipe>> = MutableLiveData()
    var errorMessage: String? = null

    fun getAllRecipes() = GlobalScope.launch(Dispatchers.IO) {
        val pos: List<Recipe>? = try {
            repository.getAllRecipes()
        } catch (e: Exception) {
            createErrorMessage(e)
            null
        }
        recipes.postValue(pos)
    }

    private fun createErrorMessage(e: Exception) {
        when (e) {
            is UnknownHostException -> {
                errorMessage = "Connection error."
            }
            is SocketTimeoutException -> {
                errorMessage = "Connection error."
            }
            is HttpException -> {
                errorMessage = "Connection error."
            }
            is ConnectException -> {
                errorMessage = "Connection error."
            }
            else -> {
                errorMessage = "Unknown error occurred."
            }
        }
    }

}