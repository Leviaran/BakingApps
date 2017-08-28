package com.singletondev.bakingapp.Adapters

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.singletondev.bakingapp.R
import com.singletondev.bakingapp.inflater
import com.singletondev.bakingapp.inputUrl
import com.singletondev.bakingapp.modelData.Recipe
import com.singletondev.bakingapp.modelData.resep
import kotlinx.android.synthetic.main.cardview_recipe_items.view.*

/**
 * Created by Randy Arba on 8/23/17.
 * This apps contains BakingApp
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */
class RecipeAdapter (private var sItemClickListener: ListItemClickListener) : RecyclerView.Adapter<RecipeAdapter.RecyclerViewHolder>() {

    lateinit var mlRecipe: ArrayList<resep>
    lateinit var mContext: Context


    interface ListItemClickListener{
        fun onItemClickListener(clickListener: resep)
    }


    fun setRecipeData(recipeIns: ArrayList<resep>, context: Context){
        this.mlRecipe = recipeIns
        this.mContext = context
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        //Inflate from Extensions.kt
        var view: View = parent.inflater(R.layout.cardview_recipe_items)
        var recyclerViewHolder = RecyclerViewHolder(view)

        return recyclerViewHolder

    }

    override fun getItemCount(): Int {
        return mlRecipe?.let { size -> size.size }
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        var imageURL: String = mlRecipe.get(position).image_cake
        holder.mTextRecyclerView.text = mlRecipe.get(position).name
        holder.mTextItemSteps.text = mlRecipe.get(position).steps.size.toString()
        holder.mTextItemServe.text = mlRecipe.get(position).ingredients.size.toString()
        holder.mimgRecyclerView.inputUrl(imageURL)

    }

    inner class RecyclerViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener   {

        var mTextRecyclerView : TextView
        var mTextItemServe : TextView
        var mTextItemSteps : TextView
        var mimgRecyclerView : ImageView

        init {
            mTextRecyclerView = itemView.findViewById(R.id.title)
            mimgRecyclerView = itemView.findViewById(R.id.recipeImage)
            mTextItemServe = itemView.findViewById(R.id.item_serves)
            mTextItemSteps = itemView.findViewById(R.id.item_steps)

            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            var clickPosition = adapterPosition
            sItemClickListener.onItemClickListener(mlRecipe.get(clickPosition))
        }

    }



}