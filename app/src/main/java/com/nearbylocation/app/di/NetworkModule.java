package com.nearbylocation.app.di;

import com.nearbylocation.app.constants.Network;
import com.nearbylocation.repository.remote.model.foursquare.FourSquareNearbyPlaces;
import com.nearbylocation.repository.remote.model.googleplaces.GoogleNearbyPlaces;
import com.nearbylocation.repository.remote.retrofit.FourSquareApi;
import com.nearbylocation.repository.remote.retrofit.GooglePlacesApi;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import javax.inject.Qualifier;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Module
public class NetworkModule {

    @Qualifier
    @Retention(RUNTIME)
    @interface FourSquareApiAnnotation{}

    @Qualifier
    @Retention(RUNTIME)
    @interface GooglePlacesApiAnnotation{}

    @Provides
    @Singleton
    @FourSquareApiAnnotation
    Retrofit provideFourSquareRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Network.BASE_URL_FOUR_SQUARE)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @GooglePlacesApiAnnotation
    Retrofit provideGooglePlacesRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Network.BASE_URL_GOOGLE_PLACES)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    FourSquareApi provideFourSquareApi(@FourSquareApiAnnotation Retrofit retrofit) {
        return retrofit.create(FourSquareApi.class);
    }

    @Provides
    @Singleton
    GooglePlacesApi provideGooglePlacesApi(@GooglePlacesApiAnnotation Retrofit retrofit) {
        return retrofit.create(GooglePlacesApi.class);
    }

    @Provides
    @Singleton
    Call<FourSquareNearbyPlaces> provideCallFourSquare(FourSquareApi fourSquareApi) {
        return fourSquareApi.fourSquareLocation(Network.FOURSQUARE_CLIENT_ID,
                Network.FOURSQUARE_CLIENT_SECRET,
                Network.FOURSQUARE_VERSION,
                Network.GEO_LOCATION);
    }

    @Provides
    @Singleton
    Call<GoogleNearbyPlaces> provideCallGooglePlaces(GooglePlacesApi googlePlacesApi) {
        return googlePlacesApi.googlePlacesLocation(Network.URL_GOOGLE_PLACES);
    }


}

//https://inducesmile.com/android/android-dagger-2-dependency-injection-with-retrofit-for-beginners/
//https://hackernoon.com/yet-another-mvp-article-part-3-calling-apis-using-retrofit-23757f4eee05
//https://github.com/mirhoseini/marvel

//passing parameters in constructor
//advice here
//https://stackoverflow.com/questions/37434657/cannot-be-provided-without-an-inject-constructor-or-from-an-provides-annotated
//not a good pattern here
//https://stackoverflow.com/a/37517764/8573120

//issue multiple baseUrl in retrofit
//1) Annotation -> https://stackoverflow.com/questions/39902710/dagger2-providing-retrofit-instances-with-different-apis-same-time
//2) Provide different version -> https://stackoverflow.com/questions/35949128/dagger2-providing-retrofit-instances-with-different-urls
//3) Three approaches - https://stackoverflow.com/a/36779923/8573120
//using 1
