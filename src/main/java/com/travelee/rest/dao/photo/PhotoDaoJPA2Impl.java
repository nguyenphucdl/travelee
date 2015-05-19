package com.travelee.rest.dao.photo;

import com.travelee.rest.helpers.NullAwareBeanUtilsBean;
import com.travelee.rest.resource.photo.Photo;
import com.travelee.rest.service.PhotoServiceDbAccessImpl;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.apache.commons.beanutils.BeanUtilsBean;

/**
 *
 * @author nguyenphucuit
 */
public class PhotoDaoJPA2Impl implements PhotoDao {

    @PersistenceContext(unitName = "demoRestPersistence")
    private EntityManager entityManager;

    @Override
    public PhotoEntity getPhotoById(long id) {
        TypedQuery<PhotoEntity> query = entityManager.createNamedQuery("PhotoEntity.findById", PhotoEntity.class);
        query.setParameter("id", id);
        PhotoEntity result = null;
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return result;
    }

    @Override
    public List<PhotoEntity> getPhotosByPlaceId(long placeId, int offset, int maxResult) {
        TypedQuery<PhotoEntity> query = entityManager.createNamedQuery("HQL_GET_PHOTOS_BY_PLACE_ID", PhotoEntity.class);
        query.setParameter("id", placeId);
        query.setFirstResult(offset);
        query.setMaxResults(maxResult);
        List<PhotoEntity> results = query.getResultList();
        return results;
    }

    @Override
    public Long createPhoto(PhotoEntity photoEntity) {
        photoEntity.setTimestamp(new Date());
        entityManager.merge(photoEntity);
        entityManager.flush();//force insert to receive the id of the podcast
        return photoEntity.getId();
    }

    @Override
    public void updatePhoto(PhotoEntity photoEntity) {
        //TODO think about partial update and full update 
//        PhotoEntity verifyPhotoExistenceById = getPhotoById(photoEntity.getId());
//        if(verifyPhotoExistenceById == null)
//        {
//            return;
//        }
//        copyPartialProperties(verifyPhotoExistenceById, photoEntity);
        entityManager.merge(photoEntity);
    }
    
    private void copyPartialProperties(PhotoEntity dest, PhotoEntity origin) {
        BeanUtilsBean notNull = new NullAwareBeanUtilsBean();
        try {
            notNull.copyProperties(dest, origin);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PhotoServiceDbAccessImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PhotoServiceDbAccessImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void deletePhotoById(Long id) {
        PhotoEntity photo = entityManager.find(PhotoEntity.class, id);
        entityManager.remove(photo);
    }

    @Override
    public List<PhotoEntity> getPhotos(Integer offset, Integer maxResult) {   
        TypedQuery<PhotoEntity> query = entityManager.createNamedQuery("PhotoEntity.findAll", PhotoEntity.class);
        query.setFirstResult(offset);
        query.setMaxResults(maxResult);
        List<PhotoEntity> results = query.getResultList();
        return results;
    }

}
