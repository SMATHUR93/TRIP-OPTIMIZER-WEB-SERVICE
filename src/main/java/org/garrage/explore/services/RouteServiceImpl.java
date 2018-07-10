package org.garrage.explore.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.garrage.explore.GoogleGateway.GoogleService;
import org.garrage.explore.GoogleGateway.model.DistanceResponse;
import org.garrage.explore.GoogleGateway.model.Element;
import org.garrage.explore.model.PlaceNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    GoogleService googleService;

    @Override
    public HashMap<String, ArrayList<Tuple>> computeOptimalRoute(ArrayList<PlaceNode> nodes) {

        HashMap<String, ArrayList<Tuple>> graph = getGraph(nodes);
        return graph;

    }

    private HashMap<String, ArrayList<Tuple>> getGraph(ArrayList<PlaceNode> nodes){
        HashMap<String, ArrayList<Tuple>> graph = new HashMap<>();
        try {
            for (PlaceNode placeNode : nodes) {
                List<PlaceNode> targetNodes = nodes.stream().filter(x -> x.getPlace_id() != placeNode.getPlace_id()).collect(Collectors.toList());
                DistanceResponse distanceResponse = googleService.getWeights(placeNode,targetNodes);
                ArrayList<Tuple> weights =  new ArrayList<>();
                List<Element> elements = distanceResponse.getRows().get(0).getElements();
                for (int i = 0; i < distanceResponse.getRows().get(0).getElements().size() ; i++) {
                    weights.add(new Tuple(targetNodes.get(i).getPlace_id(),elements.get(i).getDuration().getValue()));
                }

                graph.put(placeNode.getPlace_id(),weights);
            }

        } catch (Exception e) {
        }

        return graph;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class Tuple{
        String place_id;
        int weight;
    }

}
