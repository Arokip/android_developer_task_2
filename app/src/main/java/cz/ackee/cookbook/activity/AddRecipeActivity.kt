package cz.ackee.cookbook.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cz.ackee.cookbook.R
import cz.ackee.cookbook.data.NewRecipe
import cz.ackee.cookbook.viewmodel.AddRecipeViewModel
import kotlinx.android.synthetic.main.activity_add_recipe.*


class AddRecipeActivity : AppCompatActivity() {

    private lateinit var addRecipeViewModel: AddRecipeViewModel

    private val ingredientEditTextViewList: MutableList<EditText> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window.statusBarColor = ContextCompat.getColor(this, R.color.colorStatusBarGrey)

        setContentView(R.layout.activity_add_recipe)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        addRecipeViewModel = ViewModelProvider(this).get(AddRecipeViewModel::class.java)

        addEditTextChangeListeners()

        addIngredientButton.setOnClickListener {

            addIngredientEditText()

        }

        addRecipeViewModel.recipeDetail.observe(this, Observer { recipeDetail ->
            println("█ new recipe from server: $recipeDetail")
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_recipe, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_add_recipe) {

            val inputMethodManager =
                    getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(addIngredientButton.windowToken, 0)

            if (isEditTextEmpty(recipeNameEditText)) {
                recipeNameEditText.error = getString(R.string.required_field)
                recipeNameEditText.requestFocus()
                return false
            }

            if (!containsAckee(recipeNameEditText)) {
                recipeNameEditText.error = getString(R.string.must_contain)
                recipeNameEditText.requestFocus()
                return false
            }

            if (isEditTextEmpty(recipeInfoEditText)) {
                recipeInfoEditText.error = getString(R.string.required_field)
                recipeInfoEditText.requestFocus()
                return false
            }

            if (isEditTextEmpty(recipeProcessEditText)) {
                recipeProcessEditText.error = getString(R.string.required_field)
                recipeProcessEditText.requestFocus()
                return false
            }

            if (isEditTextEmpty(recipeTimeEditText)) {
                recipeTimeEditText.error = getString(R.string.required_field)
                recipeTimeEditText.requestFocus()
                return false
            }

            val ingredients = mutableListOf<String>()
            ingredientEditTextViewList.forEach { editText ->
                if (!isEditTextEmpty(editText)) {
                    ingredients.add(editText.text.toString())
                }
            }

            addRecipeViewModel.postNewRecipe(NewRecipe(
                    name = recipeNameEditText.text.toString(),
                    info = recipeInfoEditText.text.toString(),
                    description = recipeProcessEditText.text.toString(),
                    duration = recipeTimeEditText.text.toString().toLong(),
                    ingredients = ingredients
            ))

            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun addIngredientEditText() {
        val inflater = applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val editText: EditText = inflater.inflate(R.layout.view_edittext, ingredientsListLayout, false) as EditText

        val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.START
        layoutParams.topMargin = resources.getDimension(R.dimen.small_margin).toInt()
        editText.layoutParams = layoutParams

        ingredientsListLayout.addView(editText)

        ingredientEditTextViewList.add(editText)
    }

    private fun isEditTextEmpty(etText: EditText): Boolean {
        return etText.text.toString().trim { it <= ' ' }.isEmpty()
    }

    private fun containsAckee(etText: EditText): Boolean {
        return etText.text.toString().contains("ackee", ignoreCase = true)
    }

    private fun addEditTextChangeListeners() {
        recipeNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                recipeNameEditText.error = null
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        recipeInfoEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                recipeInfoEditText.error = null
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        recipeProcessEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                recipeProcessEditText.error = null
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        recipeTimeEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                recipeTimeEditText.error = null
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }
}