package org.garrage.explore.controller;

import org.garrage.explore.GoogleGateway.model.PlacesResponse;
import org.garrage.explore.model.GeoLocation;
import org.garrage.explore.services.PlaceSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class PlaceSearchController {

    @Autowired
    PlaceSearchService placeSearchService;

    @RequestMapping(value = "/geolocation",method = RequestMethod.GET)
    public GeoLocation getGeoLocation(@RequestParam(value = "place", required = true) String place){
        return placeSearchService.getLocation(place);
    }

    @RequestMapping(value = "/getPlaces",method = RequestMethod.GET)
    public PlacesResponse getPlaces(@RequestParam(value = "place", required = true) String place){
        return placeSearchService.getAllPlaces(place);
    }
}
