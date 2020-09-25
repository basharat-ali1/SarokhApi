package net.sarokh.api.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "Order_Pickup_Type", schema = "Sarokh_db", catalog = "")
public class OrderPickupType {
    private int id;
    private String pickupType;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pickup_type", nullable = true, length = 50)
    public String getPickupType() {
        return pickupType;
    }

    public void setPickupType(String pickupType) {
        this.pickupType = pickupType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderPickupType that = (OrderPickupType) o;

        if (id != that.id) return false;
        return pickupType != null ? pickupType.equals(that.pickupType) : that.pickupType == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (pickupType != null ? pickupType.hashCode() : 0);
        return result;
    }
}
