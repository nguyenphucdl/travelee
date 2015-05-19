package com.travelee.rest.dao.place;

import com.travelee.rest.core.AppConstants;
import com.travelee.rest.dao.photo.PhotoDao;
import com.travelee.rest.dao.photo.PhotoEntity;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author nguyenphucuit
 */
public class PlaceDaoJPA2Impl implements PlaceDao {

    @PersistenceContext(unitName = "demoRestPersistence")
    private EntityManager entityManager;

    @Autowired
    private PhotoDao photoDao;

    @Autowired
    private SessionFactory sessionFactory;

    /*
     * *********************************** READ ***********************************
     */
    @Transactional
    @Override
    public List<PlaceEntity> getPlaces(Integer offset, Integer maxResult) {
        List<PlaceEntity> results;
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {
//            Query queryTest = session.createQuery("FROM PlaceEntity as p INNER JOIN p.photoEntityList as k WHERE k.type = 0");
//            queryTest.setMaxResults(5);
//            List result = queryTest.list();
//
//            Query queryTest2;
//            queryTest2 = session.createSQLQuery("CALL `travelee_legacy`.`searchPlaceNearby3`(10.832190,106.659000,50,0,4)");
//            List<Object[]> result2 = queryTest2.list();
//            List<PlaceNearbyEntity> placeNearbyEntities = new ArrayList<>();
//            for (Object[] row : result2) {
//                PlaceNearbyEntity plNearby = new PlaceNearbyEntity();
//                plNearby.setId(Long.parseLong(row[0].toString()));
//                plNearby.setDistance(Double.parseDouble(row[17].toString()));
//                placeNearbyEntities.add(plNearby);
//            }

            TypedQuery<PlaceEntity> query = entityManager.createNamedQuery("PlaceEntity.findAll", PlaceEntity.class);
            query.setFirstResult(offset);
            query.setMaxResults(maxResult);
            results = query.getResultList();
            /*for (PlaceEntity placeEntity : results) {
             //session.refresh(placeEntity);
             Hibernate.initialize(placeEntity.getPhotoEntityList());
             }*/

        } finally {
            session.close();
        }
        return results;
    }

    @Override
    public PlaceEntity getPlaceById(Long id) {
        TypedQuery<PlaceEntity> query = entityManager.createNamedQuery("PlaceEntity.findById", PlaceEntity.class);
        query.setParameter("id", id);
        PlaceEntity placeEntity = null;
        try {
            placeEntity = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return placeEntity;
    }

    @Override
    public Long createPlace(PlaceEntity placeEntity) {
        placeEntity.setTimestamp(new Date());
        entityManager.merge(placeEntity);
        entityManager.flush();//force insert to receive the id of the place
        return placeEntity.getId();
    }

    @Override
    public List<PlaceNearbyEntity> getPlaceNearby(Double lat, Double lng, Integer distance, Integer offset, Integer maxResult) {

        String queryCallSearchNearbyProc = String.format(AppConstants.ProcedureTemplate.PROC_SEARCH_PLACE_NEARBY_TEMPLATE, lat, lng, distance, offset, maxResult);
        List<PlaceNearbyEntity> results = null;
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            Query queryCalNearbyProc = session.createSQLQuery(queryCallSearchNearbyProc)
                    .addScalar(AppConstants.Place.FIELD_ID, new LongType())
                    .addScalar(AppConstants.Place.FIELD_PLACE_ID, new StringType())
                    .addScalar(AppConstants.Place.FIELD_NAME, new StringType())
                    .addScalar(AppConstants.Place.FIELD_LATITUDE, new BigDecimalType())
                    .addScalar(AppConstants.Place.FIELD_LONGITUDE, new BigDecimalType())
                    .addScalar(AppConstants.Place.FIELD_FORMATTED_ADDRESS, new StringType())
                    .addScalar(AppConstants.Place.FIELD_FORMATTED_PHONE_NUMBER, new StringType())
                    .addScalar(AppConstants.Place.FIELD_ICON, new StringType())
                    .addScalar(AppConstants.Place.FIELD_TYPES, new StringType())
                    .addScalar(AppConstants.Place.FIELD_TAGS, new StringType())
                    .addScalar(AppConstants.Place.FIELD_INTERNATIONAL_PHONE, new StringType())
                    .addScalar(AppConstants.Place.FIELD_REFERENCE, new StringType())
                    .addScalar(AppConstants.Place.FIELD_URL, new StringType())
                    .addScalar(AppConstants.Place.FIELD_VICINITY, new StringType())
                    .addScalar(AppConstants.Place.FIELD_WEBSITE, new StringType())
                    .addScalar(AppConstants.Place.CUSTOM_FIELD_DISTANCE, new DoubleType())
                    .setResultTransformer(Transformers.aliasToBean(PlaceNearbyEntity.class));
            results = queryCalNearbyProc.list();

            fetchPhotoFromPlaceEntities(results);
        } finally {
            session.close();
        }
        return results;
    }

    @Override
    public List<PlaceNearbyEntity> getPlaceNearbyMatchTypes(Double lat, Double lng, Integer distance, String phrases, Integer offset, Integer maxResult) {
        String queryCallSearchNearbyProc = String.format(AppConstants.ProcedureTemplate.PROC_SEARCH_PLACE_NEARBY_MATCH_TYPE_TEMPLATE, lat, lng, distance, phrases, offset, maxResult);
        List<PlaceNearbyEntity> results = null;
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            Query queryCalNearbyProc = session.createSQLQuery(queryCallSearchNearbyProc)
                    .addScalar(AppConstants.Place.FIELD_ID, new LongType())
                    .addScalar(AppConstants.Place.FIELD_PLACE_ID, new StringType())
                    .addScalar(AppConstants.Place.FIELD_NAME, new StringType())
                    .addScalar(AppConstants.Place.FIELD_LATITUDE, new BigDecimalType())
                    .addScalar(AppConstants.Place.FIELD_LONGITUDE, new BigDecimalType())
                    .addScalar(AppConstants.Place.FIELD_FORMATTED_ADDRESS, new StringType())
                    .addScalar(AppConstants.Place.FIELD_FORMATTED_PHONE_NUMBER, new StringType())
                    .addScalar(AppConstants.Place.FIELD_ICON, new StringType())
                    .addScalar(AppConstants.Place.FIELD_TYPES, new StringType())
                    .addScalar(AppConstants.Place.FIELD_TAGS, new StringType())
                    .addScalar(AppConstants.Place.FIELD_INTERNATIONAL_PHONE, new StringType())
                    .addScalar(AppConstants.Place.FIELD_REFERENCE, new StringType())
                    .addScalar(AppConstants.Place.FIELD_URL, new StringType())
                    .addScalar(AppConstants.Place.FIELD_VICINITY, new StringType())
                    .addScalar(AppConstants.Place.FIELD_WEBSITE, new StringType())
                    .addScalar(AppConstants.Place.CUSTOM_FIELD_DISTANCE, new DoubleType())
                    .addScalar(AppConstants.Place.CUSTOM_FIELD_SCORE, new DoubleType())
                    .setResultTransformer(Transformers.aliasToBean(PlaceNearbyEntity.class));
            results = queryCalNearbyProc.list();

            fetchPhotoFromPlaceEntities(results);
        } finally {
            session.close();
        }
        return results;
    }

    private void fetchPhotoFromPlaceEntities(List<PlaceNearbyEntity> entities) {
        for (PlaceNearbyEntity placeEntity : entities) {
            List<PhotoEntity> photoOfIt = photoDao.getPhotosByPlaceId(placeEntity.getId(), 0, 2);
            placeEntity.setPhotoEntityList(photoOfIt);
        }
    }

    @Override
    public List<PlaceEntity> GetPlacesAndPhotos() {
        TypedQuery<PlaceEntity> query = entityManager.createNamedQuery("HQL_GET_ALL_PLACE_AND_PHOTOS", PlaceEntity.class);
        query.setMaxResults(100);
        List<PlaceEntity> results = query.getResultList();
        return results;
    }

    @Override
    public void updatePlace(PlaceEntity verifyPlaceExistenceById) {
        entityManager.merge(verifyPlaceExistenceById);
    }

}
