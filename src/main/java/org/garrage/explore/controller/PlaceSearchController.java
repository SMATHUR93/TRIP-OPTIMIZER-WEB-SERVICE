package org.garrage.explore.controller;

import org.garrage.explore.GoogleGateway.model.PlacesResponse;
import org.garrage.explore.model.GeoLocation;
import org.garrage.explore.model.PlaceNode;
import org.garrage.explore.model.PlaceSearchResponse;
import org.garrage.explore.services.PlaceSearchService;
import org.garrage.explore.services.RouteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/api")
public class PlaceSearchController {

    @Autowired
    PlaceSearchService placeSearchService;

    @Autowired
    RouteServiceImpl routeService;

    @RequestMapping(value = "/geolocation",method = RequestMethod.GET)
    public GeoLocation getGeoLocation(@RequestParam(value = "place", required = true) String place){
        return placeSearchService.getLocation(place);
    }

    @RequestMapping(value = "/getplaces",method = RequestMethod.GET)
    public PlaceSearchResponse getPlaces(@RequestParam(value = "place", required = true) String place){
        return placeSearchService.getAllPlaces(place);
    }

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public HashMap<String, ArrayList<RouteServiceImpl.Tuple>> test(@RequestBody ArrayList<PlaceNode> optimalRouteRequest ){
        return routeService.computeOptimalRoute(optimalRouteRequest);
    }
}
