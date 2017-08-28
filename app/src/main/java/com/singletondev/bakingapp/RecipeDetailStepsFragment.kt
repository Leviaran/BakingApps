package com.singletondev.bakingapp


import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.singletondev.bakingapp.modelData.Steps
import com.singletondev.bakingapp.modelData.resep
import kotlinx.android.synthetic.main.fragment_recipe_detail_steps.*
import kotlinx.android.synthetic.main.fragment_recipe_detail_steps.view.*

import java.net.URI


/**
 * A simple [Fragment] subclass.
 */
class RecipeDetailStepsFragment : Fragment() {

    companion object {
        const val ALL_RECIPES = "All_Recipes"
        const val SELECTED_RECIPES = "Selected_Recipes"
        const val SELECTED_STEPS = "Selected_Steps"
        const val SELECTED_INDEX = "Selected_Index"
        const val STACK_RECIPE_DETAIL = "STACK_RECIPE_DETAIL"
        const val STACK_RECIPE_STEP_DETAIL = "STACK_RECIPE_STEP_DETAIL"
    }
    @Nullable
    var player: SimpleExoPlayer? = null
    lateinit var simpelExoPlayerView: SimpleExoPlayerView
    lateinit var bandWidtMeter: BandwidthMeter
    lateinit var listSteps: ArrayList<Steps>
    var seletectedIndex: Int = 0
    lateinit var handler: Handler
    lateinit var recipe: ArrayList<resep>
    lateinit var recipeName: String

    lateinit var listItemClickListener: ListItemClickListener


    interface ListItemClickListener{
        fun onListItemClick(steps: List<Steps>, index: Int, recipeName: String)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        handler = Handler()
        bandWidtMeter = DefaultBandwidthMeter()

        listItemClickListener = activity as RecipeDetailACtivity
        recipe = ArrayList()

        if (savedInstanceState != null){
            listSteps = savedInstanceState.getParcelableArrayList(SELECTED_STEPS)
            seletectedIndex = savedInstanceState.getInt(SELECTED_INDEX)
            recipeName = savedInstanceState.getString("Title")
        } else {
//            listSteps = arguments.getParcelableArrayList(SELECTED_STEPS)

            if (arguments.getParcelableArrayList<Steps>(SELECTED_STEPS) != null){
                listSteps = arguments.getParcelableArrayList(SELECTED_STEPS)
                seletectedIndex = arguments.getInt(SELECTED_INDEX)
                recipeName = arguments.getString("Title")
            } else {
                recipe = arguments.getParcelableArrayList(SELECTED_RECIPES)
                listSteps = recipe.get(0).steps as ArrayList<Steps>
                seletectedIndex = 0
                recipeName = recipe.get(0).name
            }
        }

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_recipe_detail_steps,container,false)
        var textView = view.descriptionSteps
        Log.e("description"," ${listSteps.get(seletectedIndex).description} dan ${listSteps.size}")
        textView.text = listSteps.get(seletectedIndex).description
        textView.visibility = View.VISIBLE

        simpelExoPlayerView = view.simpleExoPlayer
        simpelExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT)

        var stringURL = listSteps.get(seletectedIndex).videoURL


        if (TextUtils.isEmpty(stringURL)){
            player = null
            simpelExoPlayerView.foreground = ContextCompat.getDrawable(context,R.drawable.youtube_fail2)
            simpelExoPlayerView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
            if (view.findViewWithTag<View>("view-land") != null){
                Log.e("View1","View-Land")
                activity.findViewById<View>(R.id.fragment_container2).layoutParams = LinearLayout.LayoutParams(-1,-2)
                simpelExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH)
                view.rl_navigation.visibility = View.GONE
            } else if (isInLandscapeMode(context)){
                Log.e("View2","View-Land")
                textView.visibility = View.GONE
                view.rl_navigation.visibility = View.GONE
            }
        }else{
            initPlayer(Uri.parse(listSteps.get(seletectedIndex).videoURL))
            if (view.findViewWithTag<View>("view-land") != null){
                Log.e("View1","View-Land")
                activity.findViewById<View>(R.id.fragment_container2).layoutParams = LinearLayout.LayoutParams(-1,-2)
                simpelExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH)
                view.rl_navigation.visibility = View.GONE
            } else if (isInLandscapeMode(context)){
                Log.e("View2","View-Land")
                textView.visibility = View.GONE
                view.rl_navigation.visibility = View.GONE
            }

        }

        var backButton = view.backButton
        var nextButton = view.nextButton

        backButton.setOnClickListener {
            if (listSteps.get(seletectedIndex).id > 0 ){
                if (player != null){
                    player?.stop()
                }
                listItemClickListener.onListItemClick(listSteps,listSteps.get(seletectedIndex).id -1, recipeName)
            } else {
                Toast.makeText(context,"Sudah mencapai step pertama",Toast.LENGTH_SHORT).show()
            }
        }

        nextButton.setOnClickListener {
            var lastIndex = listSteps.size - 1
            if (listSteps.get(seletectedIndex).id < listSteps.get(lastIndex).id){
                if (player != null){
                    player?.stop()
                }
                listItemClickListener.onListItemClick(listSteps,listSteps.get(seletectedIndex).id +1, recipeName)
            } else {
                Toast.makeText(context,"Anda sudah mencapai akhir step",Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    fun initPlayer(uri: Uri){
        if (player == null){
            var videoTrackSelectFactory: TrackSelection.Factory = AdaptiveVideoTrackSelection.Factory(bandWidtMeter)
            var trackSelector = DefaultTrackSelector(handler,videoTrackSelectFactory)
            var loadControl = DefaultLoadControl()

            player = ExoPlayerFactory.newSimpleInstance(context,trackSelector,loadControl)
            simpelExoPlayerView.player = player

            var userAgent = Util.getUserAgent(context, "BakingApp")
            var mediaSource = ExtractorMediaSource(uri,DefaultDataSourceFactory(context,userAgent),DefaultExtractorsFactory(),null,null)
            player?.prepare(mediaSource)
            player?.setPlayWhenReady(true)

        }
    }

    fun isInLandscapeMode(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(SELECTED_STEPS, listSteps)
        outState.putInt(SELECTED_INDEX,seletectedIndex)
        outState.putString("Title",recipeName)
    }

    override fun onDetach() {
        super.onDetach()
        if (player != null){
            player?.stop()
            player?.release()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (player != null){
            player?.stop()
            player?.release()
            player = null
        }
    }

    override fun onPause() {
        super.onPause()
        if (player != null){
            player?.stop()
            player?.release()
        }
    }

}// Required empty public constructor
