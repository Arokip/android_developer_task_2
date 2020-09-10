package cz.ackee.cookbook.data

data class RecipeDetail(
        val name: String,
        val info: String,
        val description: String,
        val duration: String,
        val ingredients: List<String>,
        val id: String,
        val score: String
)