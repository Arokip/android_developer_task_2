package cz.ackee.cookbook.repository

import cz.ackee.cookbook.api.CookbookApi


class Repository {

    var api = CookbookApi.makeRetrofitService(cookbookUrl)

    suspend fun getAllRecipes() = api.getAllRecipes()

    companion object {
        const val cookbookUrl = "https://cookbook.ack.ee/api/v1/"
    }
}