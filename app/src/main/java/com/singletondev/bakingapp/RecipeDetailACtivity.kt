package com.singletondev.bakingapp

import android.app.Fragment
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.view.ViewCompat
import android.support.v7.graphics.Palette
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.singletondev.bakingapp.Adapters.RecipeAdapter
import com.singletondev.bakingapp.Adapters.RecipeDetailAdapter
import com.singletondev.bakingapp.modelData.Steps
import com.singletondev.bakingapp.modelData.resep
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_recipe_detail_activity.*


class RecipeDetailACtivity : AppCompatActivity(), RecipeDetailAdapter.ListItemClickListerer, RecipeDetailStepsFragment.ListItemClickListener{

    override fun onListItemClick(step: List<Steps>, clickItemIndex: Int, recipeName: String) {
        Log.i("clicked","clicked")
        var recipeFragmentSteps = RecipeDetailStepsFragment()
        var fragmentManager = supportFragmentManager
        appbar.setExpanded(false)
        collapsingTollbarLayout.title = recipeName


        var bundle = Bundle()
        bundle.putParcelableArrayList(SELECTED_STEPS, step as ArrayList<Steps>)
        bundle.putInt(SELECTED_INDEX,clickItemIndex)
        bundle.putString("Title",recipeName)
        recipeFragmentSteps.arguments = bundle

        if (findViewById(R.id.recipe).tag != null && findViewById(R.id.recipe).tag.equals("view-land")){
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container2,recipeFragmentSteps)
                    .addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit()
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container,recipeFragmentSteps)
                    .addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit()
        }

    }

    companion object {
        const val ALL_RECIPES = "All_Recipes"
        const val SELECTED_RECIPES = "Selected_Recipes"
        const val SELECTED_STEPS = "Selected_Steps"
        const val SELECTED_INDEX = "Selected_Index"
        const val STACK_RECIPE_DETAIL = "STACK_RECIPE_DETAIL"
        const val STACK_RECIPE_STEP_DETAIL = "STACK_RECIPE_STEP_DETAIL"
    }

    lateinit var recipe: ArrayList<resep>
    lateinit var recipeName: String
    lateinit var collapsingTollbarLayout : CollapsingToolbarLayout
    lateinit var appbar: AppBarLayout
    var flag: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail_activity)

        if (savedInstanceState == null){
            var selectedBundle = intent.extras
            recipe = ArrayList()
            recipe = selectedBundle.getParcelableArrayList(RecipeDetailACtivity.SELECTED_RECIPES)
            recipeName = recipe.get(0).name

            var fragmentDetail = DetailFragment()
            fragmentDetail.arguments = selectedBundle
            var fragmentManager = supportFragmentManager
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragmentDetail)
                    .addToBackStack(RecipeDetailACtivity.STACK_RECIPE_DETAIL)
                    .commit()

            if (findViewById(R.id.recipe).tag != null && findViewById(R.id.recipe).tag.equals("view-land")){
                Log.e("fragment2","Fragment2")

                var fragment2 = RecipeDetailStepsFragment()
                fragment2.arguments =selectedBundle
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container2,fragment2)
                        .addToBackStack(STACK_RECIPE_STEP_DETAIL)
                        .commit()
            }

        } else {
            var selectedBundle = intent.extras
            recipe = ArrayList()
            recipe = selectedBundle.getParcelableArrayList(RecipeDetailACtivity.SELECTED_RECIPES)
            recipeName = recipe.get(0).name
            Log.e("recipeName",recipe.get(0).image_cake)
            //recipeName = savedInstanceState.getString("Title")

            if (findViewById(R.id.recipe).tag != null && findViewById(R.id.recipe).tag.equals("view-land")){
                Log.e("fragment2","Fragment2")

                var fragment3= RecipeDetailStepsFragment()
                fragment3.arguments =selectedBundle
                var fragmentManager = supportFragmentManager
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container2,fragment3)
                        .addToBackStack(STACK_RECIPE_STEP_DETAIL)
                        .commit()
            }
        }

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout),"coba")
        supportPostponeEnterTransition()

        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        collapsingTollbarLayout = findViewById(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        appbar = app_bar_layout
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            var fragmentMan = supportFragmentManager
            if (findViewById(R.id.fragment_container2) == null){
                if (fragmentMan.backStackEntryCount > 1){
                    fragmentMan.popBackStack(STACK_RECIPE_DETAIL,0)
                }else if (fragmentMan.backStackEntryCount > 0 ){
                    finish()
                }
            } else {
                finish()
            }
        }



        collapsingTollbarLayout.title = recipeName
        collapsingTollbarLayout.setExpandedTitleColor(resources.getColor(android.R.color.transparent))

        var image = findViewById(R.id.image) as ImageView
        Log.e("Cake",recipe.get(0).image_cake)

        Picasso.with(this).load("file:///android_asset/Photo_baking/${recipe.get(0).image_cake}").into(image, object : Callback{
            override fun onSuccess() {
                var bitmap = (image.drawable as BitmapDrawable).bitmap
                Palette.from(bitmap).generate { palette -> ApplyPalette(palette) }
            }

            override fun onError() {

            }
        })

//        myToolbar.setNavigationOnClickListener {
//            val fm = supportFragmentManager
//            if (findViewById(R.id.fragment_container2) == null) {
//                if (fm.backStackEntryCount > 1) {
//                    //go back to "Recipe Detail" screen
//                    fm.popBackStack(STACK_RECIPE_DETAIL, 0)
//                } else if (fm.backStackEntryCount > 0) {
//                    //go back to "Recipe" screen
//                    finish()
//
//                }
//
//
//            } else {
//
//                //go back to "Recipe" screen
//                finish()
//
//            }
//        }
    }

    fun ApplyPalette(palette: Palette){
        var primaryDark = resources.getColor(R.color.colorPrimaryDark)
        var primary = resources.getColor(R.color.colorPrimary)
        collapsingTollbarLayout.setContentScrimColor(palette.getMutedColor(primary))
        collapsingTollbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark))
        supportPostponeEnterTransition()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("Title",recipeName)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var fragmentMan = supportFragmentManager

            if (fragmentMan.backStackEntryCount > 0 ){
                fragmentMan.popBackStack(STACK_RECIPE_DETAIL,0)
            } else {
                finish()
            }
    }
}
