package org.garrage.explore.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "sequence_no",
        "geo_location",
        "place_id"
})
@Setter
@Getter
public class PlaceNode {

    @JsonProperty("sequence_no")
    int sequence_no;
    @JsonProperty("geo_location")
    GeoLocation geo_location;
    @JsonProperty("place_id")
    String place_id;

    public PlaceNode(){ }

    public PlaceNode(int seqNo, String placeId) {
        this.sequence_no = seqNo;
        this.place_id = placeId;
    }
}
