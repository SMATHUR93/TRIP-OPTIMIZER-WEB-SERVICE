package org.garrage.explore.model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OptimalRouteResponse {

    boolean status;
    List<PlaceNode> route;
}
