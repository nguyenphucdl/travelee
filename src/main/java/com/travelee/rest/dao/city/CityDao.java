package com.travelee.rest.dao.city;

import java.util.List;

/**
 *
 * @author nguyenphucuit
 */
public interface CityDao {
    public List<CityEntity> getCities();

    public CityEntity getCityById(Long id);

    public void updateCity(CityEntity cityEntity);
}
