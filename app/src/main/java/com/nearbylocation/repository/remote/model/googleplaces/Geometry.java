package com.nearbylocation.repository.remote.model.googleplaces;

/**
 * Created by shanraisshan on 10/1/2017.
 */

import com.squareup.moshi.Json;

public class Geometry {

    @Json(name = "location")
    private Location location;
    @Json(name = "viewport")
    private Viewport viewport;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

}