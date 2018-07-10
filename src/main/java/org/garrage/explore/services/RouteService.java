package org.garrage.explore.services;

import org.garrage.explore.model.OptimalRouteResponse;
import org.garrage.explore.model.PlaceNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public interface RouteService {

    HashMap<String, ArrayList<RouteServiceImpl.Tuple>> computeOptimalRoute(ArrayList<PlaceNode> nodes);

}
