package cz.ackee.cookbook.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cz.ackee.cookbook.data.Recipe
import cz.ackee.cookbook.data.RecipeDetail
import cz.ackee.cookbook.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RecipeDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository = Repository()

    val recipeDetail: MutableLiveData<RecipeDetail> = MutableLiveData()
    var errorMessage: String? = null

    fun getRecipeDetailById(id: String) = GlobalScope.launch(Dispatchers.IO) {
        val recipe: RecipeDetail? = try {
            repository.getRecipeDetailById(id)
        } catch (e: Exception) {
            createErrorMessage(e)
            null
        }
        recipeDetail.postValue(recipe)
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
                errorMessage = if ((e as HttpException).code() == 404) {
                    "Recipe not found"
                } else {
                    "Connection error."
                }
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