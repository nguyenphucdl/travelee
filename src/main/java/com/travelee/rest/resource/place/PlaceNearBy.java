package com.travelee.rest.resource.place;

import com.travelee.rest.dao.place.PlaceEntity;
import com.travelee.rest.resource.photo.PhotoList;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nguyenphucuit
 */
public class PlaceNearBy extends Place {

    private static final long serialVersionUID = -9112840248932726588L;

    public PlaceNearBy(PlaceEntity placeEntity) {
        super(placeEntity);
        
        photos = new ArrayList<>();
    }
    
    private double distance;
    
    private double score;
    
    private List<PhotoList> photos;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<PhotoList> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoList> photos) {
        this.photos = photos;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
    
    @Override
    @XmlTransient
    public byte[] getGeom() {
        return super.getGeom();
    }
}
