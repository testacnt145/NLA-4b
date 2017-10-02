package com.nearbylocation.repository.remote.model.googleplaces;

/**
 * Created by shanraisshan on 10/1/2017.
 */

import com.squareup.moshi.Json;

public class Location {

    @Json(name = "lat")
    private Double lat;
    @Json(name = "lng")
    private Double lng;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

}
