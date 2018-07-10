package org.garrage.explore.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.garrage.explore.model.GeoLocation;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OptimalRouteRequest {

    List<RouteRequest> routeRequestList;

    @Getter
    @Setter
    @AllArgsConstructor
    public class RouteRequest{
        String placeId;
        GeoLocation geoLocation;
    }
}
