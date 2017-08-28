package com.singletondev.bakingapp


import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.*
import com.singletondev.bakingapp.Adapters.RecipeAdapter
import com.singletondev.bakingapp.modelData.resep
import com.singletondev.bakingapp.network.RestAda
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject




/**
 * A simple [Fragment] subclass.
 */
class RecipeFragment : Fragment() {

    var recyclerView : RecyclerView? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater?.inflate(R.layout.fragment_recipe, container, false)
        //var view = container?.inflater(R.layout.fragment_recipe)

        initView(view)
        return view
    }

    fun initView(rootView : View?){

        if (rootView != null) {
            recyclerView = rootView.findViewById(R.id.recipe_recycler)
        }

        var recipeAdapter = RecipeAdapter(activity as Besa)
        Jsonpars(recipeAdapter)
        recyclerView?.adapter = recipeAdapter

        Log.e("viewAttach",rootView?.tag.toString())
        if (rootView?.tag!=null && rootView.tag.equals("view-land")){
            var mLayoutmanager = GridLayoutManager(context,2)
            recyclerView?.layoutManager = mLayoutmanager as RecyclerView.LayoutManager?
        } else {
            var mLinearLayout = LinearLayoutManager(context)
            recyclerView?.layoutManager = mLinearLayout
        }

    }

    fun Jsonpars(recipeAdapter: RecipeAdapter){
        var json: String? = null
        try{
            var inputStream = activity.resources.assets.open("baking.json")
            var size: Int = inputStream.available()
            var byte: ByteArray = kotlin.ByteArray(size)
            inputStream.read(byte)
            inputStream.close()
            json = String(byte)
        } catch (e: Exception){
            Log.e("Fail",e.message)
        }

        var parser = JsonParser()
        var jsonArray = parser.parse(json) as JsonArray
        var listArray : MutableList<resep> = ArrayList()

        var gson = Gson()
       // var data = gson.fromJson(jsonArray,resep::class.java)

        //Important
        jsonArray.forEach { x -> listArray.add(gson.fromJson(x,resep::class.java)) }



//       var jsonElement = jsonArray.get(2)
//        var gson = Gson()
//        var data: List<resep> = gson.fromJson(jsonArray,List<resep>::class.java)
//        Log.e("hasil",data.toString())

        var listArrayPort = listArray as ArrayList
        val bundle = Bundle()
        bundle.putParcelableArrayList(Besa.ALL_RECIPES,listArrayPort)
        recipeAdapter.setRecipeData(listArrayPort,context)

    }

//    fun initNetwork(recipeAdapter: RecipeAdapter){
//        var mRecipe = RestAda.getRespond()
//        var recipe: Observable<List<resep>> = mRecipe.resep
//
//        recipe.observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(object : Observer<List<resep>>{
//                    override fun onError(e: Throwable?) {
//                        Log.e("coba1","coba")
//                    }
//
//                    override fun onNext(t: List<resep>?) {
//                        Log.e("coba2","coba")
//                    }
//
//                    override fun onComplete() {
//                        Log.e("coba3","coba")
//                    }
//
//                    override fun onSubscribe(d: Disposable?) {
//                        Log.e("coba4","coba")
//                    }
//                })
//
//
////        recipe.enqueue(object  : Callback<List<resep>>{
////            override fun onFailure(call: Call<List<resep>>?, t: Throwable?) {
////                Log.e("Gagal",t?.message)
////            }
////
////            override fun onResponse(call: Call<List<resep>>?, response: Response<List<resep>>) {
////                Log.i("Status Code", "check")
////                var statusCod = response.code()
////
////                when (statusCod){
////                    200 -> {
////                        val recipes: ArrayList<resep> = response.body() as ArrayList<resep>
////                        val bundle = Bundle()
//////                        bundle.putParcelableArrayList(Besa.ALL_RECIPES,recipes)
//////                        recipeAdapter.setRecipeData(recipes,context)
////                    }
////                }
////            }
////        })
//    }


}