package com.travelee.rest.service;

import com.travelee.rest.errorhandling.AppException;
import com.travelee.rest.resource.place.Place;
import com.travelee.rest.resource.place.PlaceNearBy;

import java.util.List;

/**
 *
 * @author nguyenphucuit
 */
public interface PlaceService {

    public List<Place> getPlaces(Integer offset, Integer maxResult) throws AppException;

    public Place verifyPlaceExistenceById(Long id) throws AppException;

    public Long createPlace(Place place) throws AppException;

    public void updateFullyPlace(Place place) throws AppException;

    public void updatePartiallyPlace(Place place) throws AppException;

    public List<PlaceNearBy> getPlaceNearBy(Double lat, Double lng, Integer distance, String types, Integer offset, Integer maxResult) throws AppException;
}
