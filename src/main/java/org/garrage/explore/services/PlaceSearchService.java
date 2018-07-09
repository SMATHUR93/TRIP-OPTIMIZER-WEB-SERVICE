package org.garrage.explore.services;

import org.garrage.explore.GoogleGateway.model.PlacesResponse;
import org.garrage.explore.model.GeoLocation;
import org.garrage.explore.model.Place;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaceSearchService {

    GeoLocation getLocation(String place);
    PlacesResponse getAllPlaces(String place);

}
