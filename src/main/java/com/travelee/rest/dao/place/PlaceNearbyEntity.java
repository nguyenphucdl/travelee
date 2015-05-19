package com.travelee.rest.dao.place;

import com.travelee.rest.resource.place.PlaceNearBy;


public class PlaceNearbyEntity extends PlaceEntity{

    private static final long serialVersionUID = -6684230876454533315L;
        
    private Double distance; 
    
    private Double score;
    
    public PlaceNearbyEntity() {
        super();
    }
    
    public PlaceNearbyEntity(Long id) {
        super(id);
    }
    
    public PlaceNearbyEntity(PlaceNearBy place) {
        super(place);
        this.distance = place.getDistance();
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
    
}
