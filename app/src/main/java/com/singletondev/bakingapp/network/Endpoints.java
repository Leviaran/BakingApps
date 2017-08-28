package com.singletondev.bakingapp.network;

import com.singletondev.bakingapp.modelData.resep;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Randy Arba on 8/24/17.
 * This apps contains BakingApp
 *
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

public interface Endpoints{
    @GET("topher/2017/May/59121517_baking/baking.json")
    Observable<List<resep>> getResep();
}