package cz.ackee.cookbook.data

data class NewRecipe(
        val name: String,
        val info: String,
        val description: String,
        val duration: Long,
        val ingredients: List<String>
)