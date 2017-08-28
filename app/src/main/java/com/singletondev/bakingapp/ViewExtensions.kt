package com.singletondev.bakingapp

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Created by Randy Arba on 8/23/17.
 * This apps contains BakingApp
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

fun ViewGroup.inflater (id: Int, flag: Boolean = false) : View {
    return LayoutInflater.from(context).inflate(id,this,flag)
}

fun ImageView.inputUrl(url: String) {
    var pathURLLocal = "file:///android_asset/Photo_baking/$url"
    Log.e("getGambar",pathURLLocal)
    Picasso.with(context).load(pathURLLocal).into(this)
}

fun ImageView.inputURL(url: String){
    if (TextUtils.isEmpty(url)){
        Log.e("getGambar","getGambar")
        Picasso.with(context).load("file:///android_asset/Photo_baking/brownies.png").into(this)
    }else{
        Picasso.with(context).load(url).into(this)
    }

}