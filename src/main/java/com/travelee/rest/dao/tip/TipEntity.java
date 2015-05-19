package com.travelee.rest.dao.tip;

import com.travelee.rest.dao.photo.PhotoEntity;
import com.travelee.rest.dao.place.PlaceEntity;
import com.travelee.rest.dao.user.UserEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author nguyenphucuit
 */
@Entity
@Table(name = "tip")
@NamedQueries({
    @NamedQuery(name = "TipEntity.findAll", query = "SELECT t FROM TipEntity t"),
    @NamedQuery(name = "TipEntity.findById", query = "SELECT t FROM TipEntity t WHERE t.id = :id"),
    @NamedQuery(name = "TipEntity.findByDatetime", query = "SELECT t FROM TipEntity t WHERE t.datetime = :datetime")})
public class TipEntity implements Serializable {
    @OneToMany(mappedBy = "tipId", fetch = FetchType.LAZY)
    private List<PhotoEntity> photoEntityList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Lob
    @Size(max = 16777215)
    @Column(name = "tip")
    private String tip;
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

    public TipEntity() {
    }

    public TipEntity(Long id) {
        this.id = id;
    }

    public TipEntity(Long id, Date datetime) {
        this.id = id;
        this.datetime = datetime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
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
        if (!(object instanceof TipEntity)) {
            return false;
        }
        TipEntity other = (TipEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.travelee.rest.dao.tip.TipEntity[ id=" + id + " ]";
    }

    public List<PhotoEntity> getPhotoEntityList() {
        return photoEntityList;
    }

    public void setPhotoEntityList(List<PhotoEntity> photoEntityList) {
        this.photoEntityList = photoEntityList;
    }
    
}
