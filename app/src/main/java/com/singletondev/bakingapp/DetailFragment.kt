package com.singletondev.bakingapp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singletondev.bakingapp.Adapters.RecipeDetailAdapter
import com.singletondev.bakingapp.modelData.Steps
import com.singletondev.bakingapp.modelData.resep
import com.singletondev.bakingapp.widget.updateBakingServ
import kotlinx.android.synthetic.main.fragment_detail.view.*


/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    lateinit var mSteps: List<Steps>
    lateinit var mShortDesc: String
    lateinit var mDesc : String
    lateinit var recipeName: String
    lateinit var mRecipe: List<resep>

    companion object {
        const val ALL_RECIPES = "All_Recipes"
        const val SELECTED_RECIPES = "Selected_Recipes"
        const val SELECTED_STEPS = "Selected_Steps"
        const val SELECTED_INDEX = "Selected_Index"
        const val STACK_RECIPE_DETAIL = "STACK_RECIPE_DETAIL"
        const val STACK_RECIPE_STEP_DETAIL = "STACK_RECIPE_STEP_DETAIL"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        mRecipe = ArrayList()

        if (savedInstanceState != null){
            mRecipe = savedInstanceState.getParcelableArrayList(SELECTED_RECIPES)
        } else {
            mRecipe = arguments.getParcelableArrayList(SELECTED_RECIPES)
        }

        var ingridient = mRecipe.get(0).ingredients
        recipeName = mRecipe.get(0).name

        var attachView = inflater.inflate(R.layout.fragment_detail,container,false)
        var textViewDesc = attachView.recipe_detail_teks
        var recyclerView = attachView.recipe_detail_recycler

        var recipeInggridientWidget = ArrayList<String>()

        for (dataIngridients in ingridient){

            textViewDesc.append("• ${dataIngridients.ingredient} \n")
            textViewDesc.append("\t\t\t Quantity : ${dataIngridients.quantity} \n")
            textViewDesc.append("\t\t\t Measure : ${dataIngridients.measure} \n\n")

            recipeInggridientWidget.add("• ${dataIngridients.ingredient} \n" +
                    "\t\t\t Quantity : ${dataIngridients.quantity} \n" +
                    "\t\t\t Measure : ${dataIngridients.measure}")
        }

        var mLinearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = mLinearLayoutManager

        var recyclerViewadapter = RecipeDetailAdapter(activity as RecipeDetailACtivity)
        recyclerView.adapter = recyclerViewadapter
        recyclerViewadapter.setRecipeData(mRecipe,context)

        Log.e("Size",recipeInggridientWidget.size.toString())
        //Update Baking Service
        updateBakingServ.startService(context,recipeInggridientWidget,mRecipe as java.util.ArrayList<resep>)


        return attachView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(SELECTED_RECIPES, mRecipe as ArrayList)
        outState.putString("Title",recipeName)

    }

}// Required empty public constructor
