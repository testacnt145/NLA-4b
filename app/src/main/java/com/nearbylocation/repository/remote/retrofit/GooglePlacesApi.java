package com.nearbylocation.repository.remote.retrofit;

import com.nearbylocation.repository.remote.model.googleplaces.GoogleNearbyPlaces;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface GooglePlacesApi {

    @GET
    Call<GoogleNearbyPlaces> googlePlacesLocation(@Url String url);
}
