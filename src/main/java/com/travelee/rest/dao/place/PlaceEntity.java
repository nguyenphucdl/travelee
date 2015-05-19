package com.travelee.rest.dao.place;

import com.travelee.rest.dao.photo.PhotoEntity;
import com.travelee.rest.dao.rating.RatingEntity;
import com.travelee.rest.dao.tip.TipEntity;
import com.travelee.rest.helpers.BeanUtility;
import com.travelee.rest.resource.place.Place;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author nguyenphucuit
 */
@Entity
@Table(name = "place")
@NamedQueries({
    @NamedQuery(name = "PlaceEntity.findAll", query = "SELECT p FROM PlaceEntity p"),
    @NamedQuery(name = "PlaceEntity.findById", query = "SELECT p FROM PlaceEntity p WHERE p.id = :id"),
    @NamedQuery(name = "PlaceEntity.findByPlaceId", query = "SELECT p FROM PlaceEntity p WHERE p.placeId = :placeId"),
    @NamedQuery(name = "PlaceEntity.findByName", query = "SELECT p FROM PlaceEntity p WHERE p.name = :name"),
    @NamedQuery(name = "PlaceEntity.findByLat", query = "SELECT p FROM PlaceEntity p WHERE p.lat = :lat"),
    @NamedQuery(name = "PlaceEntity.findByLng", query = "SELECT p FROM PlaceEntity p WHERE p.lng = :lng"),
    @NamedQuery(name = "PlaceEntity.findByFormattedAddress", query = "SELECT p FROM PlaceEntity p WHERE p.formattedAddress = :formattedAddress"),
    @NamedQuery(name = "PlaceEntity.findByFormattedPhoneNumber", query = "SELECT p FROM PlaceEntity p WHERE p.formattedPhoneNumber = :formattedPhoneNumber"),
    @NamedQuery(name = "PlaceEntity.findByIcon", query = "SELECT p FROM PlaceEntity p WHERE p.icon = :icon"),
    @NamedQuery(name = "PlaceEntity.findByTypes", query = "SELECT p FROM PlaceEntity p WHERE p.types = :types"),
    @NamedQuery(name = "PlaceEntity.findByRating", query = "SELECT p FROM PlaceEntity p WHERE p.rating = :rating"),
    @NamedQuery(name = "PlaceEntity.findByInternationalPhoneNumber", query = "SELECT p FROM PlaceEntity p WHERE p.internationalPhoneNumber = :internationalPhoneNumber"),
    @NamedQuery(name = "PlaceEntity.findByUrl", query = "SELECT p FROM PlaceEntity p WHERE p.url = :url"),
    @NamedQuery(name = "PlaceEntity.findByVicinity", query = "SELECT p FROM PlaceEntity p WHERE p.vicinity = :vicinity"),
    @NamedQuery(name = "PlaceEntity.findByWebsite", query = "SELECT p FROM PlaceEntity p WHERE p.website = :website"),
    @NamedQuery(name = "PlaceEntity.findByTimestamp", query = "SELECT p FROM PlaceEntity p WHERE p.timestamp = :timestamp")})
public class PlaceEntity implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "geom")
    private byte[] geom;
    @Size(max = 500)
    @Column(name = "type")
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "placeId")
    private List<RatingEntity> ratingEntityList;
    @OneToMany(mappedBy = "placeId")
    private List<PhotoEntity> photoEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "placeId")
    private List<TipEntity> tipEntityList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "place_id")
    private String placeId;
    @Size(max = 1000)
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "lat")
    private BigDecimal lat;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lng")
    private BigDecimal lng;
    @Size(max = 10000)
    @Column(name = "formatted_address")
    private String formattedAddress;
    @Size(max = 150)
    @Column(name = "formatted_phone_number")
    private String formattedPhoneNumber;
    @Size(max = 200)
    @Column(name = "icon")
    private String icon;
    @Size(max = 1000)
    @Column(name = "types")
    private String types;
    @Lob
    @Size(max = 16777215)
    @Column(name = "tags")
    private String tags;
    @Column(name = "rating")
    private Float rating;
    @Size(max = 200)
    @Column(name = "international_phone_number")
    private String internationalPhoneNumber;
    @Lob
    @Size(max = 16777215)
    @Column(name = "reference")
    private String reference;
    @Size(max = 200)
    @Column(name = "url")
    private String url;
    @Size(max = 200)
    @Column(name = "vicinity")
    private String vicinity;
    @Size(max = 200)
    @Column(name = "website")
    private String website;
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public PlaceEntity() {
    }

    public PlaceEntity(Long id) {
        this.id = id;
    }

    public PlaceEntity(Place place) {
        try {
            //BeanUtils.copyProperties(this, place);
            BeanUtility.copyProperties(this, place);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PlaceEntity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PlaceEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getInternationalPhoneNumber() {
        return internationalPhoneNumber;
    }

    public void setInternationalPhoneNumber(String internationalPhoneNumber) {
        this.internationalPhoneNumber = internationalPhoneNumber;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaceEntity)) {
            return false;
        }
        PlaceEntity other = (PlaceEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.travelee.rest.dao.place.PlaceEntity[ id=" + id + " ]";
    }

    public byte[] getGeom() {
        return geom;
    }

    public void setGeom(byte[] geom) {
        this.geom = geom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<RatingEntity> getRatingEntityList() {
        return ratingEntityList;
    }

    public void setRatingEntityList(List<RatingEntity> ratingEntityList) {
        this.ratingEntityList = ratingEntityList;
    }

    public List<PhotoEntity> getPhotoEntityList() {
        return photoEntityList;
    }

    public void setPhotoEntityList(List<PhotoEntity> photoEntityList) {
        this.photoEntityList = photoEntityList;
    }

    public List<TipEntity> getTipEntityList() {
        return tipEntityList;
    }

    public void setTipEntityList(List<TipEntity> tipEntityList) {
        this.tipEntityList = tipEntityList;
    }
    
}
