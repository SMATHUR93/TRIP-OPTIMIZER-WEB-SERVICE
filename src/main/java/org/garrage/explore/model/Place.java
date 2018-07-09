package org.garrage.explore.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Place {

    UUID id;
    GeoLocation geoLocation;
    String name;
    Category category;

}
