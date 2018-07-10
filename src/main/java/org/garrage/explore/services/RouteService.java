package org.garrage.explore.services;

import org.garrage.explore.model.OptimalRouteResponse;
import org.garrage.explore.model.PlaceNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public interface RouteService {

    ArrayList<PlaceNode> computeOptimalRoute(ArrayList<PlaceNode> nodes);

}
