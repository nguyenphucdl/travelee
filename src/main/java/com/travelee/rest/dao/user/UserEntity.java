package com.travelee.rest.dao.user;

import com.travelee.rest.dao.photo.PhotoEntity;
import com.travelee.rest.dao.rating.RatingEntity;
import com.travelee.rest.dao.tip.TipEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author nguyenphucuit
 */
@Entity
@Table(name = "user")
@NamedQueries({
    @NamedQuery(name = "UserEntity.findAll", query = "SELECT u FROM UserEntity u"),
    @NamedQuery(name = "UserEntity.findById", query = "SELECT u FROM UserEntity u WHERE u.id = :id"),
    @NamedQuery(name = "UserEntity.findByName", query = "SELECT u FROM UserEntity u WHERE u.name = :name"),
    @NamedQuery(name = "UserEntity.findByAddress", query = "SELECT u FROM UserEntity u WHERE u.address = :address"),
    @NamedQuery(name = "UserEntity.findByAccessToken", query = "SELECT u FROM UserEntity u WHERE u.accessToken = :accessToken"),
    @NamedQuery(name = "UserEntity.findByType", query = "SELECT u FROM UserEntity u WHERE u.type = :type"),
    @NamedQuery(name = "UserEntity.findByLat", query = "SELECT u FROM UserEntity u WHERE u.lat = :lat"),
    @NamedQuery(name = "UserEntity.findByLng", query = "SELECT u FROM UserEntity u WHERE u.lng = :lng")})
public class UserEntity implements Serializable {
    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
    private List<PhotoEntity> photoEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId", fetch = FetchType.LAZY)
    private List<RatingEntity> ratingEntityList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId", fetch = FetchType.LAZY)
    private List<TipEntity> tipEntityList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "name")
    private String name;
    @Size(max = 1000)
    @Column(name = "address")
    private String address;
    @Size(max = 300)
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "type")
    private Integer type;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "lat")
    private BigDecimal lat;
    @Column(name = "lng")
    private BigDecimal lng;

    public UserEntity() {
    }

    public UserEntity(Long id) {
        this.id = id;
    }

    public UserEntity(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.travelee.rest.dao.user.UserEntity[ id=" + id + " ]";
    }

    public List<TipEntity> getTipEntityList() {
        return tipEntityList;
    }

    public void setTipEntityList(List<TipEntity> tipEntityList) {
        this.tipEntityList = tipEntityList;
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
    
}
