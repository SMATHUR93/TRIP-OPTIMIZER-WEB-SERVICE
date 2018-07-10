package org.garrage.explore.services;

import org.garrage.explore.GoogleGateway.GoogleService;
import org.garrage.explore.GoogleGateway.model.PlacesResponse;
import org.garrage.explore.GoogleGateway.model.Result;
import org.garrage.explore.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    public PlaceSearchResponse getAllPlaces(String place) {
        try {
            PlacesResponse placesResponse = googleService.getPlaces(place);
            PlaceSearchResponse placeSearchResponse = new PlaceSearchResponse();
            placeSearchResponse.setStatus(true);
            placeSearchResponse.setResultMap(categorizePlaces(placesResponse.getResults()));
            return placeSearchResponse;
        } catch (Exception exception) {
            return new PlaceSearchResponse();
        }
    }

    private HashMap<PlaceCategory,List<Result>> categorizePlaces(List<Result> resultList){

        HashMap<PlaceCategory,List<Result>> categorizedResults = new HashMap<PlaceCategory,List<Result>>();

        categorizedResults.put(PlaceCategory.AMUSEMENTS,
                resultList.stream().
                        filter(x->getPlaceByTypes(x,
                                Arrays.asList(PlaceType.AMUSEMENT_PARK,PlaceType.NIGHT_CLUB,PlaceType.PARK,PlaceType.AQUARIUM)))
                        .collect(Collectors.toList()));

        categorizedResults.put(PlaceCategory.RELIGOUS,
                resultList.stream().
                        filter(x->getPlaceByTypes(x,
                                Arrays.asList(PlaceType.CHURCH,PlaceType.MOSQUE,PlaceType.TEMPLE)))
                        .collect(Collectors.toList()));

        categorizedResults.put(PlaceCategory.ART,
                resultList.stream().
                        filter(x->getPlaceByTypes(x,
                                Arrays.asList(PlaceType.ART_GALLERY,PlaceType.MUSEAM)))
                        .collect(Collectors.toList()));

        categorizedResults.put(PlaceCategory.FOOD,
                resultList.stream().
                        filter(x->getPlaceByTypes(x,
                                Arrays.asList(PlaceType.RESTAURANT,PlaceType.CAFE,PlaceType.NIGHT_CLUB)))
                        .collect(Collectors.toList()));

        categorizedResults.put(PlaceCategory.LANDSCAPES,
                resultList.stream().
                        filter(x->getPlaceByTypes(x,
                                Arrays.asList(PlaceType.PARK,PlaceType.AMUSEMENT_PARK)))
                        .collect(Collectors.toList()));

        categorizedResults.put(PlaceCategory.WILDLIFE,
                resultList.stream().
                        filter(x->getPlaceByTypes(x,
                                Arrays.asList(PlaceType.AQUARIUM,PlaceType.ZOO)))
                        .collect(Collectors.toList()));

        categorizedResults.put(PlaceCategory.ALL,
                resultList);

        return categorizedResults;


    }

    private boolean getPlaceByTypes(Result result, List<PlaceType> placeTypes){
        List<String> resultTypes = result.getTypes();
        return placeTypes.stream().filter(x->resultTypes.contains(x.getKey())).findAny().isPresent();
     }

}
