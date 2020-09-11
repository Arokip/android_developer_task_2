package cz.ackee.cookbook.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cz.ackee.cookbook.data.RecipeDetail
import cz.ackee.cookbook.helper.NetworkHepler
import cz.ackee.cookbook.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecipeDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository = Repository()

    val recipeDetail: MutableLiveData<RecipeDetail> = MutableLiveData()
    var errorMessage: String? = null

    fun getRecipeDetailById(id: String) = GlobalScope.launch(Dispatchers.IO) {
        val recipe: RecipeDetail? = try {
            repository.getRecipeDetailById(id)
        } catch (e: Exception) {
            errorMessage = NetworkHepler.createErrorMessage(e)
            null
        }
        recipeDetail.postValue(recipe)
    }

    fun makeIngredientsString(ingredients: List<String>?): String {
        return if (ingredients.isNullOrEmpty()) {
            ""
        } else {
            ingredients.joinToString(separator = "\n") { ingredientItem ->
                "•   $ingredientItem"
            }
        }
    }

}