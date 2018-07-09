package org.garrage.explore.services;

import org.garrage.explore.GoogleGateway.GoogleService;
import org.garrage.explore.GoogleGateway.model.PlacesResponse;
import org.garrage.explore.model.GeoLocation;
import org.garrage.explore.model.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceSearchServiceImpl implements PlaceSearchService {

    @Autowired
    GoogleService googleService;

    @Override
    public GeoLocation getLocation(String place) {
        try {
            return googleService.getGeoLocation(place);
        }catch (Exception exception){
            return new GeoLocation();
        }
    }

    @Override
    public PlacesResponse getAllPlaces(String place) {
        try {
            PlacesResponse placesResponse = googleService.getPlaces(place);

        } catch (Exception exception) {
            return new PlacesResponse();
        }
    }
}
