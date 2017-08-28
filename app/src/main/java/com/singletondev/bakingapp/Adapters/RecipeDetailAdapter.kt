package com.singletondev.bakingapp.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.singletondev.bakingapp.R
import com.singletondev.bakingapp.inflater
import com.singletondev.bakingapp.inputURL
import com.singletondev.bakingapp.inputUrl
import com.singletondev.bakingapp.modelData.Steps
import com.singletondev.bakingapp.modelData.resep
import kotlinx.android.synthetic.main.carview_recipe_items_detail.*
import kotlinx.android.synthetic.main.carview_recipe_items_detail.view.*

/**
 * Created by Randy Arba on 8/24/17.
 * This apps contains BakingApp
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */


class RecipeDetailAdapter (private val listItemClick: ListItemClickListerer) : RecyclerView.Adapter<RecipeDetailAdapter.RecyclerViewHolder>() {

    lateinit var mSteps: List<Steps>
    lateinit var mShortDesc: String
    lateinit var mDesc : String
    lateinit var recipeName: String

    interface ListItemClickListerer{
        fun onListItemClick(step: List<Steps>,clickItemIndex: Int, recipeName: String)
    }

    fun setRecipeData(listRecipe: List<resep>, context: Context){
        mSteps = listRecipe.get(0).steps
        recipeName = listRecipe.get(0).name
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: RecyclerViewHolder?, position: Int) {
        holder?.textShortDesc?.text = mSteps.get(position).shortDescription
        holder?.textDesc?.text = mSteps.get(position).description
        holder?.imageThumbnail?.inputURL(mSteps.get(position).thumbnailURL)
    }

    override fun getItemCount(): Int {
        return mSteps.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        var view = parent.inflater(R.layout.carview_recipe_items_detail)

        var recyclerViewHolder = RecyclerViewHolder(view)

        return recyclerViewHolder
    }

    inner class RecyclerViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var imageThumbnail : ImageView
        var textShortDesc : TextView
        var textDesc: TextView

        init {
            imageThumbnail = itemView.recipeImage
            textShortDesc = itemView.shor_description_text
            textDesc = itemView.description_text

            itemView.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            var clickPosition = adapterPosition
            listItemClick.onListItemClick(mSteps,clickPosition,recipeName)

        }

    }
}