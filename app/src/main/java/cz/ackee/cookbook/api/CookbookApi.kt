package cz.ackee.cookbook.api

import cz.ackee.cookbook.data.Recipe
import cz.ackee.cookbook.data.RecipeDetail
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface CookbookApi {

    @GET("recipes")
    suspend fun getAllRecipes(): List<Recipe>

    @GET("recipes/{id}")
    suspend fun getRecipeDetailById(@Path("id") id: String): RecipeDetail

    @FormUrlEncoded
    @POST("recipes")
    suspend fun postNewRecipe(
            @Field("name") name: String,
            @Field("description") description: String,
            @Field("ingredients") ingredients: List<String>,
            @Field("duration") duration: Long,
            @Field("info") info: String
    ): RecipeDetail

    companion object {

        fun makeRetrofitService(baseUrl: String): CookbookApi {

            val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()

            return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
                    .create(CookbookApi::class.java)
        }
    }

}