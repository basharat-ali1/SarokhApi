package net.sarokh.api.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Shipper_tarrif", schema = "Sarokh_db")
public class ShipperTariff {
    private int id;
    private Double deliverySameCity;
    private Double deliveryDomesticT1;
    private Double deliveryDomesticT2;
    private Double deliveryDomesticT3;
    private Double insuranceCost;
    private Double cod;
    private Double lastMileCharges;
    private Double cancellationCharges;
    private Double returnCharges;
    private Double changeLocationCharges;
    private Timestamp createdDatetime;
    private Timestamp updatedDatetime;
    private int shipperShipperId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "delivery_same_city", nullable = true, precision = 0)
    public Double getDeliverySameCity() {
        return deliverySameCity;
    }

    public void setDeliverySameCity(Double deliverySameCity) {
        this.deliverySameCity = deliverySameCity;
    }

    @Basic
    @Column(name = "delivery_domestic_t1", nullable = true, precision = 0)
    public Double getDeliveryDomesticT1() {
        return deliveryDomesticT1;
    }

    public void setDeliveryDomesticT1(Double deliveryDomesticT1) {
        this.deliveryDomesticT1 = deliveryDomesticT1;
    }

    @Basic
    @Column(name = "delivery_domestic_t2", nullable = true, precision = 0)
    public Double getDeliveryDomesticT2() {
        return deliveryDomesticT2;
    }

    public void setDeliveryDomesticT2(Double deliveryDomesticT2) {
        this.deliveryDomesticT2 = deliveryDomesticT2;
    }

    @Basic
    @Column(name = "delivery_domestic_t3", nullable = true, precision = 0)
    public Double getDeliveryDomesticT3() {
        return deliveryDomesticT3;
    }

    public void setDeliveryDomesticT3(Double deliveryDomesticT3) {
        this.deliveryDomesticT3 = deliveryDomesticT3;
    }

    @Basic
    @Column(name = "insurance_cost", nullable = true, precision = 0)
    public Double getInsuranceCost() {
        return insuranceCost;
    }

    public void setInsuranceCost(Double insuranceCost) {
        this.insuranceCost = insuranceCost;
    }

    @Basic
    @Column(name = "COD", nullable = true, precision = 0)
    public Double getCod() {
        return cod;
    }

    public void setCod(Double cod) {
        this.cod = cod;
    }

    @Basic
    @Column(name = "last_mile_charges", nullable = true, precision = 0)
    public Double getLastMileCharges() {
        return lastMileCharges;
    }

    public void setLastMileCharges(Double lastMileCharges) {
        this.lastMileCharges = lastMileCharges;
    }

    @Basic
    @Column(name = "cancellation_charges", nullable = true, precision = 0)
    public Double getCancellationCharges() {
        return cancellationCharges;
    }

    public void setCancellationCharges(Double cancellationCharges) {
        this.cancellationCharges = cancellationCharges;
    }

    @Basic
    @Column(name = "return_charges", nullable = true, precision = 0)
    public Double getReturnCharges() {
        return returnCharges;
    }

    public void setReturnCharges(Double returnCharges) {
        this.returnCharges = returnCharges;
    }

    @Basic
    @Column(name = "change_location_charges", nullable = true, precision = 0)
    public Double getChangeLocationCharges() {
        return changeLocationCharges;
    }

    public void setChangeLocationCharges(Double changeLocationCharges) {
        this.changeLocationCharges = changeLocationCharges;
    }

    @Basic
    @Column(name = "created_datetime", nullable = true)
    public Timestamp getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Timestamp createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    @Basic
    @Column(name = "updated_datetime", nullable = true)
    public Timestamp getUpdatedDatetime() {
        return updatedDatetime;
    }

    public void setUpdatedDatetime(Timestamp updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
    }

    @Basic
    @Column(name = "Shipper_shipper_id", nullable = false)
    public int getShipperShipperId() {
        return shipperShipperId;
    }

    public void setShipperShipperId(int shipperShipperId) {
        this.shipperShipperId = shipperShipperId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipperTariff that = (ShipperTariff) o;

        if (id != that.id) return false;
        if (shipperShipperId != that.shipperShipperId) return false;
        if (deliverySameCity != null ? !deliverySameCity.equals(that.deliverySameCity) : that.deliverySameCity != null)
            return false;
        if (deliveryDomesticT1 != null ? !deliveryDomesticT1.equals(that.deliveryDomesticT1) : that.deliveryDomesticT1 != null)
            return false;
        if (deliveryDomesticT2 != null ? !deliveryDomesticT2.equals(that.deliveryDomesticT2) : that.deliveryDomesticT2 != null)
            return false;
        if (deliveryDomesticT3 != null ? !deliveryDomesticT3.equals(that.deliveryDomesticT3) : that.deliveryDomesticT3 != null)
            return false;
        if (insuranceCost != null ? !insuranceCost.equals(that.insuranceCost) : that.insuranceCost != null)
            return false;
        if (cod != null ? !cod.equals(that.cod) : that.cod != null) return false;
        if (lastMileCharges != null ? !lastMileCharges.equals(that.lastMileCharges) : that.lastMileCharges != null)
            return false;
        if (cancellationCharges != null ? !cancellationCharges.equals(that.cancellationCharges) : that.cancellationCharges != null)
            return false;
        if (returnCharges != null ? !returnCharges.equals(that.returnCharges) : that.returnCharges != null)
            return false;
        if (changeLocationCharges != null ? !changeLocationCharges.equals(that.changeLocationCharges) : that.changeLocationCharges != null)
            return false;
        if (createdDatetime != null ? !createdDatetime.equals(that.createdDatetime) : that.createdDatetime != null)
            return false;
        return updatedDatetime != null ? updatedDatetime.equals(that.updatedDatetime) : that.updatedDatetime == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (deliverySameCity != null ? deliverySameCity.hashCode() : 0);
        result = 31 * result + (deliveryDomesticT1 != null ? deliveryDomesticT1.hashCode() : 0);
        result = 31 * result + (deliveryDomesticT2 != null ? deliveryDomesticT2.hashCode() : 0);
        result = 31 * result + (deliveryDomesticT3 != null ? deliveryDomesticT3.hashCode() : 0);
        result = 31 * result + (insuranceCost != null ? insuranceCost.hashCode() : 0);
        result = 31 * result + (cod != null ? cod.hashCode() : 0);
        result = 31 * result + (lastMileCharges != null ? lastMileCharges.hashCode() : 0);
        result = 31 * result + (cancellationCharges != null ? cancellationCharges.hashCode() : 0);
        result = 31 * result + (returnCharges != null ? returnCharges.hashCode() : 0);
        result = 31 * result + (changeLocationCharges != null ? changeLocationCharges.hashCode() : 0);
        result = 31 * result + (createdDatetime != null ? createdDatetime.hashCode() : 0);
        result = 31 * result + (updatedDatetime != null ? updatedDatetime.hashCode() : 0);
        result = 31 * result + shipperShipperId;
        return result;
    }
}
