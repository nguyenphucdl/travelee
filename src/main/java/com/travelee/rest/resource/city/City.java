package com.travelee.rest.resource.city;

import com.travelee.rest.dao.city.CityEntity;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.beanutils.BeanUtils;

/**
 * City resource placeholder for json/xml representation
 *
 * @author nguyenphucuit
 */
@SuppressWarnings("restriction")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class City implements Serializable {

    private static final long serialVersionUID = -9042413675517189612L;

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "asciiname")
    private String asciiname;

    @XmlElement(name = "alternatenames")
    private String alternatenames;

    @XmlElement(name = "geom")
    private byte[] geom;

    @XmlElement(name = "lat")
    private BigDecimal lat;

    @XmlElement(name = "lng")
    private BigDecimal lng;

    @XmlElement(name = "featureClass")
    private String featureClass;

    @XmlElement(name = "featureCode")
    private String featureCode;

    public City() {
    }

    public City(CityEntity cityEntity) {
        try {
            BeanUtils.copyProperties(this, cityEntity);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(City.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(City.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public City(Long id, byte[] geom, BigDecimal lat, BigDecimal lng) {
        this.id = id;
        this.geom = geom;
        this.lat = lat;
        this.lng = lng;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAsciiname() {
        return asciiname;
    }

    public void setAsciiname(String asciiname) {
        this.asciiname = asciiname;
    }

    public String getAlternatenames() {
        return alternatenames;
    }

    public void setAlternatenames(String alternatenames) {
        this.alternatenames = alternatenames;
    }

    public byte[] getGeom() {
        return geom;
    }

    public void setGeom(byte[] geom) {
        this.geom = geom;
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

    public String getFeatureClass() {
        return featureClass;
    }

    public void setFeatureClass(String featureClass) {
        this.featureClass = featureClass;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
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
        if (!(object instanceof City)) {
            return false;
        }
        City other = (City) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.travelee.rest.resource.city.City[ id=" + id + " ]";
    }

}
