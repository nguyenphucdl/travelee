package com.travelee.rest.dao.city;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author nguyenphucuit
 */
public class CityDaoJPA2Impl implements CityDao {

    @PersistenceContext(unitName = "demoRestPersistence")
    private EntityManager entityManager;

    @Override
    public List<CityEntity> getCities() {
        TypedQuery<CityEntity> query = entityManager.createNamedQuery("CityEntity.findAll", CityEntity.class);
        query.setMaxResults(200);
        List<CityEntity> results = query.getResultList();
        return results;
    }

    @Override
    public CityEntity getCityById(Long id) {
        TypedQuery<CityEntity> query = entityManager.createNamedQuery("CityEntity.findById", CityEntity.class);
        query.setParameter("id", id);
        CityEntity cityEntity = null;
        try {
            cityEntity = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return cityEntity;
    }

    @Override
    public void updateCity(CityEntity cityEntity) {
        
    }
}
