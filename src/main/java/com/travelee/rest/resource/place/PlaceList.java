package com.travelee.rest.resource.place;

import com.travelee.rest.dao.place.PlaceEntity;
import com.travelee.rest.resource.photo.PhotoList;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nguyenphucuit
 */
public class PlaceList extends Place {

    private static final long serialVersionUID = 6640669732072524475L;

    private List<PhotoList> photos;

    public PlaceList(PlaceEntity placeEntity) {
        super(placeEntity);

        photos = new ArrayList<>();
    }

    @XmlElement(name = "avatars", nillable = true)
    public List<PhotoList> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoList> photos) {
        this.photos = photos;
    }

    @Override
    @XmlTransient
    public byte[] getGeom() {
        return super.getGeom();
    }

    @Override
    @XmlTransient
    public String getPlaceId() {
        return super.getPlaceId();
    }

    @Override
    @XmlTransient
    public String getReference() {
        return super.getReference();
    }
}
