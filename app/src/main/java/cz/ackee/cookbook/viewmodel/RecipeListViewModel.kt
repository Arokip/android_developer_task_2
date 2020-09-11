package cz.ackee.cookbook.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cz.ackee.cookbook.data.Recipe
import cz.ackee.cookbook.helper.NetworkHelper
import cz.ackee.cookbook.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecipeListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository = Repository()
    private val appContext = application.applicationContext

    val recipes: MutableLiveData<List<Recipe>> = MutableLiveData()
    var errorMessage: String? = null

    fun getAllRecipes() = GlobalScope.launch(Dispatchers.IO) {
        val recipeList: List<Recipe>? = try {
            repository.getAllRecipes()
        } catch (e: Exception) {
            errorMessage = NetworkHelper.createErrorMessage(appContext, e)
            null
        }
        recipes.postValue(recipeList)
    }

}