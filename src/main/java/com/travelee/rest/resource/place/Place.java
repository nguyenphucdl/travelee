package com.travelee.rest.resource.place;

import com.travelee.rest.dao.place.PlaceEntity;
import com.travelee.rest.hateoas.StateLink;
import com.travelee.rest.helpers.BeanUtility;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Place resource placeholder for json/xml representation
 *
 * @author nguyenphucuit
 */
@SuppressWarnings("restriction")
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Place extends PlaceResponse implements Serializable {

    private static final long serialVersionUID = -554005439288356220L;

    private Long id;

    private String placeId;

    private String name;

    private byte[] geom;

    private BigDecimal lat;

    private BigDecimal lng;

    private String formattedAddress;

    private String formattedPhoneNumber;

    private String icon;

    private String types;

    private String tags;

    private Float rating;

    private String internationalPhoneNumber;

    private String reference;

    private String url;

    private String vicinity;

    private String website;

    private List<StateLink> links;

    public Place() {
    }

    public Place(PlaceEntity placeEntity) {
        try {
            //BeanUtils.copyProperties(this, placeEntity);
            BeanUtility.copyProperties(this, placeEntity);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
        }
        links = new ArrayList<>();
    }

    public Place(Long id, String placeId, byte[] geom) {
        this.id = id;
        this.placeId = placeId;
        this.geom = geom;
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

    public byte[] getGeom() {
        return geom;
    }

    public void setGeom(byte[] geom) {
        this.geom = geom;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Place)) {
            return false;
        }
        Place other = (Place) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.travelee.rest.dao.place.Place[ id=" + id + " ]";
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

    public List<StateLink> getLinks() {
        return links;
    }

    public void setLinks(List<StateLink> links) {
        this.links = links;
    }

    public void addLink(StateLink link) {
        links.add(link);
    }

    public void removeLink(StateLink link) {
        links.remove(link);
    }
}
