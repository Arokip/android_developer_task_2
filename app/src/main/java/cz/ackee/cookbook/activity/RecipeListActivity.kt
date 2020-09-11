package cz.ackee.cookbook.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cz.ackee.cookbook.R
import cz.ackee.cookbook.view.RecipesRecyclerViewAdapter
import cz.ackee.cookbook.viewmodel.RecipeListViewModel
import kotlinx.android.synthetic.main.activity_recipe_list.*

class RecipeListActivity : AppCompatActivity(),
        RecipesRecyclerViewAdapter.ItemClickListener {

    private lateinit var recipeListViewModel: RecipeListViewModel

    private lateinit var adapter: RecipesRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window.statusBarColor = ContextCompat.getColor(this, R.color.colorStatusBarGrey)

        setContentView(R.layout.activity_recipe_list)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        recipeListViewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)

        adapter = RecipesRecyclerViewAdapter()
        adapter.setClickListener(this)
        adapter.setRecipes(emptyList())

        recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        recipeRecyclerView.adapter = adapter

        recipeListViewModel.getAllRecipes()

        recipeListViewModel.recipes.observe(this, Observer { recipes ->

            when {
                recipes == null -> {
                    errorText.visibility = View.VISIBLE
                    errorText.text = recipeListViewModel.errorMessage
                    return@Observer
                }
                recipes.isEmpty() -> {
                    errorText.visibility = View.VISIBLE
                    errorText.text = getString(R.string.no_recipe_available)
                    return@Observer
                }
                else -> {
                    adapter.setRecipes(recipes)
                }
            }

            progressBar.visibility = View.GONE
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

    override fun onItemClick(view: View, position: Int) {
        val id = recipeListViewModel.recipes.value?.get(position)?.id
        RecipeDetailActivity.start(this, id)
    }

}
