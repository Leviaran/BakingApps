package com.singletondev.bakingapp.modelData

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Randy Arba on 8/23/17.
 * This apps contains BakingApp
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */


data class Recipe(val id: Int,
                  val name: String,
                  val ingredients: List<Ingredients>,
                  val steps: List<Steps>,
                  val servings: Int,
                  val image: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            ArrayList<Ingredients>().apply { source.readList(this, Ingredients::class.java.classLoader) },
            ArrayList<Steps>().apply { source.readList(this, Steps::class.java.classLoader) },
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(name)
        writeList(ingredients)
        writeList(steps)
        writeInt(servings)
        writeString(image)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Recipe> = object : Parcelable.Creator<Recipe> {
            override fun createFromParcel(source: Parcel): Recipe = Recipe(source)
            override fun newArray(size: Int): Array<Recipe?> = arrayOfNulls(size)
        }
    }
}
