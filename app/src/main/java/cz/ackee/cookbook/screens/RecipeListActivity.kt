package cz.ackee.cookbook.screens

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cz.ackee.cookbook.R
import cz.ackee.cookbook.viewmodels.RecipeListModel

class RecipeListActivity : AppCompatActivity() {

    private lateinit var recipeListModel: RecipeListModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window.statusBarColor = ContextCompat.getColor(this, R.color.colorStatusBarGrey)

        setContentView(R.layout.activity_recipe_list)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        recipeListModel = ViewModelProvider(this).get(RecipeListModel::class.java)

        recipeListModel.getAllRecipes()

        recipeListModel.recipes.observe(this, Observer { recipes ->

            if (recipes == null) {
                println("nullllllll")
                return@Observer
            }

            recipes.forEach { recipe ->
                println(recipe)
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_recipe_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_add_recipe) {
            startActivity(Intent(this, AddRecipeActivity::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}
