package cz.ackee.cookbook.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cz.ackee.cookbook.data.NewRecipe
import cz.ackee.cookbook.data.RecipeDetail
import cz.ackee.cookbook.helper.NetworkHelper
import cz.ackee.cookbook.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddRecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository = Repository()

    val recipeDetail: MutableLiveData<RecipeDetail> = MutableLiveData()
    var errorMessage: String? = null

    fun postNewRecipe(newRecipe: NewRecipe) = GlobalScope.launch(Dispatchers.IO) {
        val recipeDetailResponse: RecipeDetail? = try {
            repository.postNewRecipe(newRecipe)
        } catch (e: Exception) {
            errorMessage = NetworkHelper.createErrorMessage(e)
            null
        }
        recipeDetail.postValue(recipeDetailResponse)

        // TODO: add to recipeList (+DB)
    }


}