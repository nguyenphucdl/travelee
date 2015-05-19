package com.travelee.rest.dao.place;

import java.util.List;

/**
 *
 * @author nguyenphucuit
 */
public interface PlaceDao {

    //public List<PlaceEntity> getPlaces();
    
    public List<PlaceEntity> getPlaces(Integer offset, Integer maxResult);

    public PlaceEntity getPlaceById(Long id);
    
    public List<PlaceEntity> GetPlacesAndPhotos();    

    public List<PlaceNearbyEntity> getPlaceNearby(Double lat, Double lng, Integer distance, Integer offset, Integer maxResult);
    
    public List<PlaceNearbyEntity> getPlaceNearbyMatchTypes(Double lat, Double lng, Integer distance, String phrases, Integer offset, Integer maxResult);

    public Long createPlace(PlaceEntity placeEntity);

    public void updatePlace(PlaceEntity verifyPlaceExistenceById);
}
