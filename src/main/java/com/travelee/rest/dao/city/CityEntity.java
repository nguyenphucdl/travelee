package com.travelee.rest.dao.city;

import com.travelee.rest.resource.city.City;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author nguyenphucuit
 */
@Entity
@Table(name = "cities")
@NamedQueries({
    @NamedQuery(name = "CityEntity.findAll", query = "SELECT c FROM CityEntity c"),
    @NamedQuery(name = "CityEntity.findById", query = "SELECT c FROM CityEntity c WHERE c.id = :id"),
    @NamedQuery(name = "CityEntity.findByName", query = "SELECT c FROM CityEntity c WHERE c.name = :name"),
    @NamedQuery(name = "CityEntity.findByAsciiname", query = "SELECT c FROM CityEntity c WHERE c.asciiname = :asciiname"),
    @NamedQuery(name = "CityEntity.findByLat", query = "SELECT c FROM CityEntity c WHERE c.lat = :lat"),
    @NamedQuery(name = "CityEntity.findByLng", query = "SELECT c FROM CityEntity c WHERE c.lng = :lng"),
    @NamedQuery(name = "CityEntity.findByFeatureClass", query = "SELECT c FROM CityEntity c WHERE c.featureClass = :featureClass"),
    @NamedQuery(name = "CityEntity.findByFeatureCode", query = "SELECT c FROM CityEntity c WHERE c.featureCode = :featureCode")})
public class CityEntity implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "geom")
    private byte[] geom;
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 200)
    @Column(name = "name")
    private String name;
    @Size(max = 200)
    @Column(name = "asciiname")
    private String asciiname;
    @Lob
    @Size(max = 16777215)
    @Column(name = "alternatenames")
    private String alternatenames;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "lat")
    private BigDecimal lat;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lng")
    private BigDecimal lng;
    @Size(max = 30)
    @Column(name = "feature_class")
    private String featureClass;
    @Size(max = 30)
    @Column(name = "feature_code")
    private String featureCode;

    public CityEntity() {
    }

    public CityEntity(City city) {
        try {
            BeanUtils.copyProperties(this, city);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CityEntity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(CityEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public CityEntity(Long id, byte[] geom, BigDecimal lat, BigDecimal lng) {
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
        if (!(object instanceof CityEntity)) {
            return false;
        }
        CityEntity other = (CityEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.travelee.rest.dao.city.CityEntity[ id=" + id + " ]";
    }

    public byte[] getGeom() {
        return geom;
    }

    public void setGeom(byte[] geom) {
        this.geom = geom;
    }
    
}
