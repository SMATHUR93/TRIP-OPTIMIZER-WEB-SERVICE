package org.garrage.explore.model;

public enum PlaceCategory {

    ALL("all"),FOOD("food"), AMUSEMENTS("amusements"), LANDSCAPES("landscapes"), WILDLIFE("wildlife"),
    RELIGOUS("religious"), ART("art");

    private String key;

    PlaceCategory(String key){
        this.key=key;
    }

    public String getKey(){
        return this.key;
    }
}
