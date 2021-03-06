package org.garrage.explore.GoogleGateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.garrage.explore.GoogleGateway.model.DistanceResponse;
import org.garrage.explore.GoogleGateway.model.GoogleResponse;
import org.garrage.explore.GoogleGateway.model.PlacesResponse;
import org.garrage.explore.model.GeoLocation;
import org.garrage.explore.model.Place;
import org.garrage.explore.model.PlaceNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

@Service
public class GoogleService {

    final private String key="AIzaSyCNzudxWQmZi5hC3nHE2w8e0HPwg1sgOSw";

    public GeoLocation getGeoLocation(String place) throws Exception {
        String url="https://maps.googleapis.com/maps/api/place/findplacefromtext/json?inputtype=textquery&fields=photos,formatted_address,name,rating,opening_hours,geometry&input="+place+"&key="+key;
        ResponseEntity<String> responseEntity = getRequest(url);
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            ObjectMapper objectMapper = new ObjectMapper();
            GeoLocation geoLocation = new GeoLocation();
            GoogleResponse googleResponse= objectMapper.readValue(responseEntity.getBody(),GoogleResponse.class);
            geoLocation.setLat(googleResponse.getCandidates().get(0).getGeometry().getLocation().getLat());
            geoLocation.setLng(googleResponse.getCandidates().get(0).getGeometry().getLocation().getLng());

            return geoLocation;
        }

        throw new Exception();
    }

    public PlacesResponse getPlaces(String place) throws Exception{
        GeoLocation geoLocation = getGeoLocation(place);
        String url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?radius=1500&keyword=tourists attractions&key="+key+"&location="+geoLocation.getLat()+","+geoLocation.getLng();
        ResponseEntity<String> responseEntity = getRequest(url);
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Place> places = new ArrayList<Place>();
            PlacesResponse placesResponse= objectMapper.readValue(responseEntity.getBody(),PlacesResponse.class);
            return placesResponse;
        }

        throw new Exception();
    }

    private ResponseEntity<String> getRequest(String url){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(url,String.class);
    }

    public DistanceResponse getWeights(PlaceNode source, List<PlaceNode> destinations) throws Exception{
        String sourceLoc = source.getGeo_location().getLat()+","+source.getGeo_location().getLng();
        String destLocs = new String();
        for (PlaceNode destination:
             destinations) {
            destLocs+=destination.getGeo_location().getLat()+","+destination.getGeo_location().getLng()+"|";
        }

        String url="https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+sourceLoc+"&destinations="+destLocs+"&key="+key;

        ResponseEntity<String> responseEntity = getRequest(url);
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Place> places = new ArrayList<Place>();
            try{
            DistanceResponse distanceResponse = objectMapper.readValue(responseEntity.getBody(), DistanceResponse.class);
            return distanceResponse;
        }catch (Exception e){

                throw e;
            }
        }

        return new DistanceResponse();
    }


}
