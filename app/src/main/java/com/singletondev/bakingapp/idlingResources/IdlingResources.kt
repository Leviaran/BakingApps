package com.singletondev.bakingapp.idlingResources

import android.support.annotation.Nullable
import android.support.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean


/**
 * Created by Randy Arba on 8/24/17.
 * This apps contains BakingApp
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

open class IdlingResources : IdlingResource  {

    @Nullable
    lateinit var mCallback: IdlingResource.ResourceCallback

    var mIdleControl = AtomicBoolean(true)

    override fun getName(): String {
        return this.javaClass.name
    }

    override fun isIdleNow(): Boolean {
        return mIdleControl.get()
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.mCallback = callback
    }

    /**
     * @param bernilai tidak bila ada operasi yang dipending
     */
    fun setIdleController(isIdle: Boolean){
        mIdleControl.set(isIdle)
        if (isIdle && mCallback != null){
            mCallback.onTransitionToIdle()
        }
    }

}