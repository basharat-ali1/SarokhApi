package net.sarokh.api.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "Shipper_Crew", schema = "Sarokh_db", catalog = "")
public class ShipperCrew {
    private int id;
    private int shipperId;
    private int userId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "shipper_id", nullable = false)
    public int getShipperId() {
        return shipperId;
    }

    public void setShipperId(int shipperId) {
        this.shipperId = shipperId;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipperCrew that = (ShipperCrew) o;

        if (id != that.id) return false;
        if (shipperId != that.shipperId) return false;
        return userId == that.userId;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + shipperId;
        result = 31 * result + userId;
        return result;
    }
}
