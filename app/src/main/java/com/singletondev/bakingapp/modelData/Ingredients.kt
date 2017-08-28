package com.singletondev.bakingapp.modelData

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Randy Arba on 8/23/17.
 * This apps contains BakingApp
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

data class Ingredients(
        val quantity: Double,
        val measure: String,
        val ingredient: String
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readDouble(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeDouble(quantity)
        writeString(measure)
        writeString(ingredient)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Ingredients> = object : Parcelable.Creator<Ingredients> {
            override fun createFromParcel(source: Parcel): Ingredients = Ingredients(source)
            override fun newArray(size: Int): Array<Ingredients?> = arrayOfNulls(size)
        }
    }
}