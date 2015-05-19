package com.travelee.rest.service;


import com.travelee.rest.errorhandling.AppException;
import com.travelee.rest.resource.city.City;

import java.util.List;

/**
 *
 * @author nguyenphucuit
 */
public interface CityService {

    /*
     ******************** Read related methods ********************
     */
    /**
     *
     * @param orderByInsertionDate - if set, it represents the order by criteria
     * (ASC or DESC) for displaying City
     * @param numberDaysToLookBack - if set, it represents number of days to
     * look back for City, null
     * @return list with City coressponding to search criterias
     * @throws AppException
     */
    public List<City> getCities() throws AppException;

    /**
     * Returns a podcast given its id
     *
     * @param id
     * @return
     * @throws AppException
     */
    public City getCityById(Long id) throws AppException;

    public Long createCity(City city) throws AppException;
    
    public City verifyCityExistenceById(Long id) throws AppException;    

    public void updateFullyCity(City city) throws AppException;

    public void updatePartiallyCity(City city) throws AppException;
}
