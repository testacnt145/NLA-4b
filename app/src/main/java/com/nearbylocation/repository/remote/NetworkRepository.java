package com.nearbylocation.repository.remote;

import com.nearbylocation.repository.remote.model.foursquare.FourSquareNearbyPlaces;
import com.nearbylocation.repository.remote.model.googleplaces.GoogleNearbyPlaces;
import com.nearbylocation.app.di.DaggerNetworkComponent;
import com.nearbylocation.app.di.NetworkComponent;
import com.nearbylocation.app.di.NetworkModule;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import javax.inject.Inject;
import android.support.annotation.NonNull;

public class NetworkRepository implements Repository {

    @Inject
    Call<FourSquareNearbyPlaces> callFourSquare;
    @Inject
    Call<GoogleNearbyPlaces> callGooglePlaces;

    public NetworkRepository() {
        NetworkComponent networkComponent = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule())
                .build();
        networkComponent.inject(this);
    }


    @Override
    public void getLocationFromFourSquare(GeneralCallback<FourSquareNearbyPlaces> callback) {
        callFourSquare.clone().enqueue(new Callback<FourSquareNearbyPlaces>() {
            @Override
            public void onResponse(@NonNull Call<FourSquareNearbyPlaces> call, @NonNull Response<FourSquareNearbyPlaces> response) {
                callback.onResponse(call, response);
            }
            @Override
            public void onFailure(@NonNull Call<FourSquareNearbyPlaces> call, @NonNull Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    @Override
    public void getLocationFromGooglePlaces(GeneralCallback<GoogleNearbyPlaces> callback) {
        callGooglePlaces.clone().enqueue(new Callback<GoogleNearbyPlaces>() {
            @Override
            public void onResponse(@NonNull Call<GoogleNearbyPlaces> call, @NonNull Response<GoogleNearbyPlaces> response) {
               callback.onResponse(call, response);
            }
            @Override
            public void onFailure(@NonNull Call<GoogleNearbyPlaces> call, @NonNull Throwable t) {
               callback.onFailure(call, t);
            }
        });
    }

    @Override
    public void clear() {
        //LogUtil.e(getClass().getSimpleName(), "clear");
        callFourSquare.cancel();
        callGooglePlaces.cancel();
    }
}
