package cz.ackee.cookbook.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cz.ackee.cookbook.R
import cz.ackee.cookbook.viewmodel.RecipeDetailViewModel

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var recipeDetailViewModel: RecipeDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = ContextCompat.getColor(context, R.color.colorStatusBarTransparent)
        }

        setContentView(R.layout.activity_recipe_detail)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        recipeDetailViewModel = ViewModelProvider(this).get(RecipeDetailViewModel::class.java)

        val recipeId = intent.getStringExtra(idString)

        if (recipeId == null) {
            finish()
            Toast.makeText(this, recipeDetailViewModel.errorMessage, Toast.LENGTH_LONG).show()
        } else {
            recipeDetailViewModel.getRecipeDetailById(recipeId)
        }

        recipeDetailViewModel.recipeDetail.observe(this, Observer { recipeDetail ->
            // TODO: set ui
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_recipe_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_add_recipe) {
            startActivity(Intent(this, AddRecipeActivity::class.java))
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {

        const val idString = "ID"

        fun start(context: Context, id: String?) {
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(idString, id)
            context.startActivity(intent)
        }

    }
}