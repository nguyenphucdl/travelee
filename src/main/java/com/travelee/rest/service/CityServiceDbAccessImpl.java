package com.travelee.rest.service;

import com.travelee.rest.core.AppConstants;
import com.travelee.rest.dao.city.CityDao;
import com.travelee.rest.dao.city.CityEntity;
import com.travelee.rest.errorhandling.AppException;
import com.travelee.rest.helpers.NullAwareBeanUtilsBean;
import com.travelee.rest.resource.city.City;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.commons.beanutils.BeanUtilsBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nguyenphucuit
 */
public class CityServiceDbAccessImpl implements CityService {

    @Autowired
    CityDao cityDao;

    @Override
    public List<City> getCities() throws AppException {
        List<CityEntity> cityEntities = cityDao.getCities();
//        cityEntities = null;
//        if(cityEntities == null)
//        {
//            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Please set either ASC or DESC for the orderByInsertionDate parameter", null, AppConstants.BLOG_POST_URL);
//        }
        return getCityFromEntities(cityEntities);
    }

    private List<City> getCityFromEntities(List<CityEntity> cityEntities) {
        List<City> response = new ArrayList<City>();
        for (CityEntity cityEntity : cityEntities) {
            response.add(new City(cityEntity));
        }
        return response;
    }

    @Override
    public City getCityById(Long id) throws AppException {
        CityEntity cityEntity = cityDao.getCityById(id);

        return new City(cityEntity);
    }

    @Override
    public Long createCity(City city) {
        return 10000L;
    }

    @Override
    public City verifyCityExistenceById(Long id) throws AppException {
        CityEntity cityEntity = cityDao.getCityById(id);
        if (cityEntity == null) {
            return null;
        } else {
            return new City(cityEntity);
        }
    }

    /**
     * ******************* UPDATE-related methods implementation
     * **********************
     */
    @Override
    @Transactional("transactionManager")
    public void updateFullyCity(City city) throws AppException {
        //do a validation to verify FULL update with PUT
        if (isFullUpdate(city)) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
                    404,
                    "The resource you are trying to update does not exist in the database",
                    "Please verify existence of data in the database for the id - " + city.getId(),
                    AppConstants.BLOG_POST_URL);
        }

        City verifyCityExistenceById = verifyCityExistenceById(city.getId());
        if (verifyCityExistenceById == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
                    404,
                    "The resource you are trying to update does not exist in the database",
                    "Please verify existence of data in the database for the id - " + city.getId(),
                    AppConstants.BLOG_POST_URL);
        }
        cityDao.updateCity(new CityEntity(city));
    }

    private boolean isFullUpdate(City city) {
        return city.getId() == null
                || city.getName() == null
                || city.getAsciiname() == null
                || city.getFeatureClass() == null
                || city.getFeatureCode() == null;
    }

    @Transactional("transactionManager")
    public void updatePartiallyCity(City city) throws AppException {
        //do a validation to verify existence of the resource
        City verifyCityExistenceById = verifyCityExistenceById(city.getId());
        if (verifyCityExistenceById == null) {
            throw new AppException(Response.Status.NOT_FOUND.getStatusCode(),
                    404,
                    "The resource you are trying to update does not exist in the database",
                    "Please verify existence of data in the database for the id - " + city.getId(),
                    AppConstants.BLOG_POST_URL);
        }
        copyPartialProperties(verifyCityExistenceById, city);
        cityDao.updateCity(new CityEntity(verifyCityExistenceById));
    }

    private void copyPartialProperties(City verifyCityExistenceById, City city) {
        BeanUtilsBean notNull = new NullAwareBeanUtilsBean();
        try {
            notNull.copyProperties(verifyCityExistenceById, city);
        } catch (IllegalAccessException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }

    }
}
