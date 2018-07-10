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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    GoogleService googleService;

    @Override
    public ArrayList<PlaceNode> computeOptimalRoute(ArrayList<PlaceNode> nodes) {

        HashMap<String, ArrayList<Tuple>> graph = getGraph(nodes);
        ArrayList<PlaceNode> sequentialPlaceNodes = getSequentialPlaceNodes(graph);
        return sequentialPlaceNodes;

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

    private ArrayList<PlaceNode> getSequentialPlaceNodes(HashMap<String, ArrayList<Tuple>> graph){
        ArrayList<PlaceNode> placeListWithIds = new ArrayList<PlaceNode>();
        int seqNo = 0;
        String placeId;
        ArrayList<Tuple> listPlaces;
        String closestPlaceId = null;
        ArrayList<String> visited = new ArrayList();

        Map.Entry<String,ArrayList<Tuple>> entry  = graph.entrySet().iterator().next();
        placeId = entry.getKey();
        listPlaces = entry.getValue();
        placeListWithIds.add(new PlaceNode(seqNo,closestPlaceId));
        int size = graph.size();
        int count = 1;
        while(count <= size){
            if(visited.contains(placeId)){
                count+=1;
                continue;
            }
            visited.add(placeId);
            closestPlaceId = getClosestPlaceFromParent(listPlaces);
            placeListWithIds.add(new PlaceNode(seqNo, closestPlaceId));
            listPlaces = graph.get(closestPlaceId);
            placeId = closestPlaceId;
            seqNo += 1;
            count += 1;
        }
        /*for(Map.Entry<String,ArrayList<Tuple>> entry: graph.entrySet()){
            placeId = entry.getKey();
            if(visited.size() == 0) {
                visited.add(placeId);
                placeListWithIds.add(new PlaceNode(seqNo, closestPlaceId));
            }
            if(visited.contains(placeId)){
                continue;
            }
            listPlaces = entry.getValue();
            closestPlaceId = getClosestPlaceFromParent(listPlaces);
            placeListWithIds.add(new PlaceNode(seqNo, closestPlaceId));
            seqNo += 1;
        }*/
        return placeListWithIds;

    }

    private String getClosestPlaceFromParent(ArrayList<Tuple> listPlaces){

        /*int minWeight = listPlaces.get(0).getWeight();
        String placeId =listPlaces.get(0).getPlace_id();
        for (Tuple tuple: listPlaces) {
            if(minWeight < tuple.getWeight()){
                placeId = tuple.getPlace_id();
                minWeight = tuple.getWeight();
            }
        }*/
        return listPlaces.stream().min(Comparator.comparing(Tuple::getWeight)).get().getPlace_id();

        //return placeId;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public class Tuple{
        String place_id;
        int weight;
    }

}
