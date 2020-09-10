package cz.ackee.cookbook.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cz.ackee.cookbook.R
import cz.ackee.cookbook.data.Recipe

class RecipesRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mClickListener: ItemClickListener? = null

    private var recipes = emptyList<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val recipeView =
                inflater.inflate(R.layout.item_recyclerview_recipe, parent, false)

        return ViewHolder(recipeView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder as ViewHolder
        val recipeItem = recipes[position]

        holder.recipeName.text = recipeItem.name

        holder.recipeScoreLayout.removeAllViews()
        for (i in 1..(recipeItem.score.toDouble().toInt())) {
            val imageView = ImageView(holder.recipeScoreLayout.context)
            imageView.setImageDrawable(ContextCompat.getDrawable(holder.recipeScoreLayout.context, R.drawable.ic_star))
            holder.recipeScoreLayout.addView(imageView)
        }

        holder.recipeDuration.text = holder.recipeDuration.context.getString(R.string.recipe_duration, recipeItem.duration)

    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    inner class ViewHolder internal constructor(itemView: View) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var recipeName: TextView = itemView.findViewById(R.id.recipeName)
        internal var recipeScoreLayout: LinearLayout = itemView.findViewById(R.id.recipeScoreLayout)
        internal var recipeDuration: TextView = itemView.findViewById(R.id.recipeDuration)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

    }

    internal fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    internal fun setRecipes(times: List<Recipe>?) {
        if (times != null) {
            this.recipes = times
        }
        notifyDataSetChanged()
    }
}