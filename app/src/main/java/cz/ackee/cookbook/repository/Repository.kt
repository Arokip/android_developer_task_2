package cz.ackee.cookbook.repository

import cz.ackee.cookbook.api.CookbookApi
import cz.ackee.cookbook.data.NewRecipe
import cz.ackee.cookbook.data.RecipeDetail


class Repository {

    var api = CookbookApi.makeRetrofitService(cookbookUrl)

    suspend fun getAllRecipes() = api.getAllRecipes()

    suspend fun getRecipeDetailById(id: String) = api.getRecipeDetailById(id)

    suspend fun postNewRecipe(recipe: NewRecipe) = api.postNewRecipe(recipe.name, recipe.description, recipe.ingredients, recipe.duration, recipe.info)

    companion object {
        const val cookbookUrl = "https://cookbook.ack.ee/api/v1/"
    }
}