package org.garrage.explore.model;

public enum PlaceType {

    AMUSEMENT_PARK("amusement_park"), AQUARIUM("aquarium"), ART_GALLERY("art_gallery"),
    CHURCH("church"), TEMPLE("hindu_temple"), MOSQUE("mosque"),
    MUSEAM("museum"), NIGHT_CLUB("night_club"), PARK("park"),
    RESTAURANT("restaurant"), CAFE("cafe"), SHOPPING_MALL("shopping_mall"), SPA("spa"),
    STADIUM("stadium"), ZOO("zoo");

    private String key;

    PlaceType(String key){
        this.key=key;
    }

    public String getKey(){
        return key;
    }
}
