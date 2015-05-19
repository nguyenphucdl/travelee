package com.travelee.rest.dao.rating;

import com.travelee.rest.dao.place.PlaceEntity;
import com.travelee.rest.dao.user.UserEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author nguyenphucuit
 */
@Entity
@Table(name = "rating")
@NamedQueries({
    @NamedQuery(name = "RatingEntity.findAll", query = "SELECT r FROM RatingEntity r"),
    @NamedQuery(name = "RatingEntity.findById", query = "SELECT r FROM RatingEntity r WHERE r.id = :id"),
    @NamedQuery(name = "RatingEntity.findByScore", query = "SELECT r FROM RatingEntity r WHERE r.score = :score"),
    @NamedQuery(name = "RatingEntity.findByDatetime", query = "SELECT r FROM RatingEntity r WHERE r.datetime = :datetime")})
public class RatingEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "score")
    private Integer score;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PlaceEntity placeId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserEntity userId;

    public RatingEntity() {
    }

    public RatingEntity(Long id) {
        this.id = id;
    }

    public RatingEntity(Long id, Date datetime) {
        this.id = id;
        this.datetime = datetime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public PlaceEntity getPlaceId() {
        return placeId;
    }

    public void setPlaceId(PlaceEntity placeId) {
        this.placeId = placeId;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
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
        if (!(object instanceof RatingEntity)) {
            return false;
        }
        RatingEntity other = (RatingEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.travelee.rest.dao.rating.RatingEntity[ id=" + id + " ]";
    }
    
}
