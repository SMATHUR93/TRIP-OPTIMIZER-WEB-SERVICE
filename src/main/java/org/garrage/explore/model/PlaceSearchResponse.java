package org.garrage.explore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.garrage.explore.GoogleGateway.model.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
public class PlaceSearchResponse {

    private String searchLocation;
    private GeoLocation parrentLocation;
    private Map<PlaceCategory,List<Result>> resultMap = new HashMap<PlaceCategory,List<Result>>();
    private Boolean status;

    public void addToResult(PlaceCategory placeCategory,Result result){
        resultMap.get(placeCategory).add(result);
    }
}

