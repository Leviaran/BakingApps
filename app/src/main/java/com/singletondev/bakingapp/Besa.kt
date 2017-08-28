package com.singletondev.bakingapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.annotation.VisibleForTesting
import android.support.test.espresso.IdlingPolicies
import android.support.test.espresso.IdlingResource
import com.singletondev.bakingapp.Adapters.RecipeAdapter
import com.singletondev.bakingapp.idlingResources.IdlingResources
import com.singletondev.bakingapp.modelData.Recipe
import com.singletondev.bakingapp.modelData.resep


class Besa : AppCompatActivity(), RecipeAdapter.ListItemClickListener {

    @Nullable
    var mIdlingRes : IdlingResources? = null

    companion object {
        const val ALL_RECIPES = "All_Recipes"
        const val SELECTED_RECIPES = "Selected_Recipes"
        const val SELECTED_STEPS = "Selected_Steps"
        const val SELECTED_INDEX = "Selected_Index"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_besa)

        initToolbar()

        getIdleRes()

    }

    @VisibleForTesting
    @NonNull
    fun getIdleRes() : IdlingResource {
        if (mIdlingRes == null){
            mIdlingRes = IdlingResources()
        }
        return mIdlingRes as IdlingResources
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    fun initToolbar(){
        var toolbar = findViewById(R.id.my_toolbar) as android.support.v7.widget.Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setTitle("Baking Time")
    }

    override fun onItemClickListener(clickListener: resep) {
        var selectrecipeBundle = Bundle()
        var selectedRecipe: ArrayList<resep> = ArrayList()
        selectedRecipe.add(clickListener)
        selectrecipeBundle.putParcelableArrayList(SELECTED_RECIPES, selectedRecipe)

        var intent = Intent(this, RecipeDetailACtivity::class.java)
        intent.putExtras(selectrecipeBundle)
        startActivity(intent)

    }

}
