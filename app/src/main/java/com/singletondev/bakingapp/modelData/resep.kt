package com.singletondev.bakingapp.modelData

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Randy Arba on 8/24/17.
 * This apps contains BakingApp
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */


data class resep(
        var id: Int,
        var name: String,
        var image_cake: String,
        var ingredients: List<Ingredients>,
        var steps: List<Steps>,
        var servings: Int,
        var image: String
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.createTypedArrayList(Ingredients.CREATOR),
            source.createTypedArrayList(Steps.CREATOR),
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(name)
        writeString(image_cake)
        writeTypedList(ingredients)
        writeTypedList(steps)
        writeInt(servings)
        writeString(image)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<resep> = object : Parcelable.Creator<resep> {
            override fun createFromParcel(source: Parcel): resep = resep(source)
            override fun newArray(size: Int): Array<resep?> = arrayOfNulls(size)
        }
    }
}