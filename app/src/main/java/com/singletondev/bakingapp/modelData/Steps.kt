package com.singletondev.bakingapp.modelData

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Randy Arba on 8/23/17.
 * This apps contains BakingApp
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

data class Steps(
        val id: Int,
        val shortDescription: String,
        val description: String,
        val videoURL: String,
        val thumbnailURL: String
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(shortDescription)
        writeString(description)
        writeString(videoURL)
        writeString(thumbnailURL)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Steps> = object : Parcelable.Creator<Steps> {
            override fun createFromParcel(source: Parcel): Steps = Steps(source)
            override fun newArray(size: Int): Array<Steps?> = arrayOfNulls(size)
        }
    }
}