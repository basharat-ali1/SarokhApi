package net.sarokh.api.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Shipment", schema = "Sarokh_db", catalog = "")
public class Shipment {
    private int id;
    private String shipmentDetail;
    private String shipmentType;
    private Double codAmount;
    private Double codCharges;
    private Double shipmentValue;
    private Double shipmentCost;
    private String activated;
    private String status;
    private Timestamp pickupDatetime;
    private String pickupReceiving;
    private Timestamp deliveryDueDate;
    private String deliveryTye;
    private String deliveryLocation;
    private String deliveryCity;
    private String deliveryLatitude;
    private String deliveryLongitude;
    private Timestamp deliveryDueDatetime;
    private Timestamp deliveryDatetime;
    private String dealerReceiving;
    private Timestamp dealerReceivingDatetime;
    private Timestamp receiverDatetime;
    private String receiverReceiving;
    private String shipperWayBill;
    private Integer recoveryId;
    private String dimensions;
    private Double weight;
    private int shipmentOrderId;
    private int receiverId;
    private int dealerId;
    private int dispatchTripId;
    private int sarokhWarehouseId;
    private int zoneId;
    private int storageLocationsId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "shipment_detail", nullable = true, length = 45)
    public String getShipmentDetail() {
        return shipmentDetail;
    }

    public void setShipmentDetail(String shipmentDetail) {
        this.shipmentDetail = shipmentDetail;
    }

    @Basic
    @Column(name = "shipment_type", nullable = true, length = 45)
    public String getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }

    @Basic
    @Column(name = "COD_amount", nullable = true, precision = 0)
    public Double getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(Double codAmount) {
        this.codAmount = codAmount;
    }

    @Basic
    @Column(name = "COD_charges", nullable = true, precision = 0)
    public Double getCodCharges() {
        return codCharges;
    }

    public void setCodCharges(Double codCharges) {
        this.codCharges = codCharges;
    }

    @Basic
    @Column(name = "shipment_value", nullable = true, precision = 0)
    public Double getShipmentValue() {
        return shipmentValue;
    }

    public void setShipmentValue(Double shipmentValue) {
        this.shipmentValue = shipmentValue;
    }

    @Basic
    @Column(name = "shipment_cost", nullable = true, precision = 0)
    public Double getShipmentCost() {
        return shipmentCost;
    }

    public void setShipmentCost(Double shipmentCost) {
        this.shipmentCost = shipmentCost;
    }

    @Basic
    @Column(name = "activated", nullable = true, length = 45)
    public String getActivated() {
        return activated;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 45)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "pickup_datetime", nullable = true)
    public Timestamp getPickupDatetime() {
        return pickupDatetime;
    }

    public void setPickupDatetime(Timestamp pickupDatetime) {
        this.pickupDatetime = pickupDatetime;
    }

    @Basic
    @Column(name = "pickup_receiving", nullable = true, length = 45)
    public String getPickupReceiving() {
        return pickupReceiving;
    }

    public void setPickupReceiving(String pickupReceiving) {
        this.pickupReceiving = pickupReceiving;
    }

    @Basic
    @Column(name = "delivery_due_date", nullable = true)
    public Timestamp getDeliveryDueDate() {
        return deliveryDueDate;
    }

    public void setDeliveryDueDate(Timestamp deliveryDueDate) {
        this.deliveryDueDate = deliveryDueDate;
    }

    @Basic
    @Column(name = "delivery_tye", nullable = true, length = 45)
    public String getDeliveryTye() {
        return deliveryTye;
    }

    public void setDeliveryTye(String deliveryTye) {
        this.deliveryTye = deliveryTye;
    }

    @Basic
    @Column(name = "delivery_location", nullable = true, length = 45)
    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    @Basic
    @Column(name = "delivery_city", nullable = true, length = 45)
    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    @Basic
    @Column(name = "delivery_latitude", nullable = true, length = 45)
    public String getDeliveryLatitude() {
        return deliveryLatitude;
    }

    public void setDeliveryLatitude(String deliveryLatitude) {
        this.deliveryLatitude = deliveryLatitude;
    }

    @Basic
    @Column(name = "delivery_longitude", nullable = true, length = 45)
    public String getDeliveryLongitude() {
        return deliveryLongitude;
    }

    public void setDeliveryLongitude(String deliveryLongitude) {
        this.deliveryLongitude = deliveryLongitude;
    }

    @Basic
    @Column(name = "delivery_due_datetime", nullable = true)
    public Timestamp getDeliveryDueDatetime() {
        return deliveryDueDatetime;
    }

    public void setDeliveryDueDatetime(Timestamp deliveryDueDatetime) {
        this.deliveryDueDatetime = deliveryDueDatetime;
    }

    @Basic
    @Column(name = "delivery_datetime", nullable = true)
    public Timestamp getDeliveryDatetime() {
        return deliveryDatetime;
    }

    public void setDeliveryDatetime(Timestamp deliveryDatetime) {
        this.deliveryDatetime = deliveryDatetime;
    }

    @Basic
    @Column(name = "dealer_receiving", nullable = true, length = 45)
    public String getDealerReceiving() {
        return dealerReceiving;
    }

    public void setDealerReceiving(String dealerReceiving) {
        this.dealerReceiving = dealerReceiving;
    }

    @Basic
    @Column(name = "dealer_receiving_datetime", nullable = true)
    public Timestamp getDealerReceivingDatetime() {
        return dealerReceivingDatetime;
    }

    public void setDealerReceivingDatetime(Timestamp dealerReceivingDatetime) {
        this.dealerReceivingDatetime = dealerReceivingDatetime;
    }

    @Basic
    @Column(name = "receiver_datetime", nullable = true)
    public Timestamp getReceiverDatetime() {
        return receiverDatetime;
    }

    public void setReceiverDatetime(Timestamp receiverDatetime) {
        this.receiverDatetime = receiverDatetime;
    }

    @Basic
    @Column(name = "receiver_receiving", nullable = true, length = 45)
    public String getReceiverReceiving() {
        return receiverReceiving;
    }

    public void setReceiverReceiving(String receiverReceiving) {
        this.receiverReceiving = receiverReceiving;
    }

    @Basic
    @Column(name = "shipper_way_bill", nullable = true, length = 45)
    public String getShipperWayBill() {
        return shipperWayBill;
    }

    public void setShipperWayBill(String shipperWayBill) {
        this.shipperWayBill = shipperWayBill;
    }

    @Basic
    @Column(name = "recovery_id", nullable = true)
    public Integer getRecoveryId() {
        return recoveryId;
    }

    public void setRecoveryId(Integer recoveryId) {
        this.recoveryId = recoveryId;
    }

    @Basic
    @Column(name = "dimensions", nullable = true, length = 45)
    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    @Basic
    @Column(name = "weight", nullable = true, precision = 0)
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "shipment_order_id", nullable = false)
    public int getShipmentOrderId() {
        return shipmentOrderId;
    }

    public void setShipmentOrderId(int shipmentOrderId) {
        this.shipmentOrderId = shipmentOrderId;
    }

    @Basic
    @Column(name = "receiver_id", nullable = false)
    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    @Basic
    @Column(name = "dealer_id", nullable = false)
    public int getDealerId() {
        return dealerId;
    }

    public void setDealerId(int dealerId) {
        this.dealerId = dealerId;
    }

    @Basic
    @Column(name = "dispatch_trip_id", nullable = false)
    public int getDispatchTripId() {
        return dispatchTripId;
    }

    public void setDispatchTripId(int dispatchTripId) {
        this.dispatchTripId = dispatchTripId;
    }

    @Basic
    @Column(name = "sarokh_warehouse_id", nullable = false)
    public int getSarokhWarehouseId() {
        return sarokhWarehouseId;
    }

    public void setSarokhWarehouseId(int sarokhWarehouseId) {
        this.sarokhWarehouseId = sarokhWarehouseId;
    }

    @Basic
    @Column(name = "zone_id", nullable = false)
    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    @Basic
    @Column(name = "storage_locations_id", nullable = false)
    public int getStorageLocationsId() {
        return storageLocationsId;
    }

    public void setStorageLocationsId(int storageLocationsId) {
        this.storageLocationsId = storageLocationsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shipment that = (Shipment) o;

        if (id != that.id) return false;
        if (shipmentOrderId != that.shipmentOrderId) return false;
        if (receiverId != that.receiverId) return false;
        if (dealerId != that.dealerId) return false;
        if (dispatchTripId != that.dispatchTripId) return false;
        if (sarokhWarehouseId != that.sarokhWarehouseId) return false;
        if (zoneId != that.zoneId) return false;
        if (storageLocationsId != that.storageLocationsId) return false;
        if (shipmentDetail != null ? !shipmentDetail.equals(that.shipmentDetail) : that.shipmentDetail != null)
            return false;
        if (shipmentType != null ? !shipmentType.equals(that.shipmentType) : that.shipmentType != null) return false;
        if (codAmount != null ? !codAmount.equals(that.codAmount) : that.codAmount != null) return false;
        if (codCharges != null ? !codCharges.equals(that.codCharges) : that.codCharges != null) return false;
        if (shipmentValue != null ? !shipmentValue.equals(that.shipmentValue) : that.shipmentValue != null)
            return false;
        if (shipmentCost != null ? !shipmentCost.equals(that.shipmentCost) : that.shipmentCost != null) return false;
        if (activated != null ? !activated.equals(that.activated) : that.activated != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (pickupDatetime != null ? !pickupDatetime.equals(that.pickupDatetime) : that.pickupDatetime != null)
            return false;
        if (pickupReceiving != null ? !pickupReceiving.equals(that.pickupReceiving) : that.pickupReceiving != null)
            return false;
        if (deliveryDueDate != null ? !deliveryDueDate.equals(that.deliveryDueDate) : that.deliveryDueDate != null)
            return false;
        if (deliveryTye != null ? !deliveryTye.equals(that.deliveryTye) : that.deliveryTye != null) return false;
        if (deliveryLocation != null ? !deliveryLocation.equals(that.deliveryLocation) : that.deliveryLocation != null)
            return false;
        if (deliveryCity != null ? !deliveryCity.equals(that.deliveryCity) : that.deliveryCity != null) return false;
        if (deliveryLatitude != null ? !deliveryLatitude.equals(that.deliveryLatitude) : that.deliveryLatitude != null)
            return false;
        if (deliveryLongitude != null ? !deliveryLongitude.equals(that.deliveryLongitude) : that.deliveryLongitude != null)
            return false;
        if (deliveryDueDatetime != null ? !deliveryDueDatetime.equals(that.deliveryDueDatetime) : that.deliveryDueDatetime != null)
            return false;
        if (deliveryDatetime != null ? !deliveryDatetime.equals(that.deliveryDatetime) : that.deliveryDatetime != null)
            return false;
        if (dealerReceiving != null ? !dealerReceiving.equals(that.dealerReceiving) : that.dealerReceiving != null)
            return false;
        if (dealerReceivingDatetime != null ? !dealerReceivingDatetime.equals(that.dealerReceivingDatetime) : that.dealerReceivingDatetime != null)
            return false;
        if (receiverDatetime != null ? !receiverDatetime.equals(that.receiverDatetime) : that.receiverDatetime != null)
            return false;
        if (receiverReceiving != null ? !receiverReceiving.equals(that.receiverReceiving) : that.receiverReceiving != null)
            return false;
        if (shipperWayBill != null ? !shipperWayBill.equals(that.shipperWayBill) : that.shipperWayBill != null)
            return false;
        if (recoveryId != null ? !recoveryId.equals(that.recoveryId) : that.recoveryId != null) return false;
        if (dimensions != null ? !dimensions.equals(that.dimensions) : that.dimensions != null) return false;
        return weight != null ? weight.equals(that.weight) : that.weight == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (shipmentDetail != null ? shipmentDetail.hashCode() : 0);
        result = 31 * result + (shipmentType != null ? shipmentType.hashCode() : 0);
        result = 31 * result + (codAmount != null ? codAmount.hashCode() : 0);
        result = 31 * result + (codCharges != null ? codCharges.hashCode() : 0);
        result = 31 * result + (shipmentValue != null ? shipmentValue.hashCode() : 0);
        result = 31 * result + (shipmentCost != null ? shipmentCost.hashCode() : 0);
        result = 31 * result + (activated != null ? activated.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (pickupDatetime != null ? pickupDatetime.hashCode() : 0);
        result = 31 * result + (pickupReceiving != null ? pickupReceiving.hashCode() : 0);
        result = 31 * result + (deliveryDueDate != null ? deliveryDueDate.hashCode() : 0);
        result = 31 * result + (deliveryTye != null ? deliveryTye.hashCode() : 0);
        result = 31 * result + (deliveryLocation != null ? deliveryLocation.hashCode() : 0);
        result = 31 * result + (deliveryCity != null ? deliveryCity.hashCode() : 0);
        result = 31 * result + (deliveryLatitude != null ? deliveryLatitude.hashCode() : 0);
        result = 31 * result + (deliveryLongitude != null ? deliveryLongitude.hashCode() : 0);
        result = 31 * result + (deliveryDueDatetime != null ? deliveryDueDatetime.hashCode() : 0);
        result = 31 * result + (deliveryDatetime != null ? deliveryDatetime.hashCode() : 0);
        result = 31 * result + (dealerReceiving != null ? dealerReceiving.hashCode() : 0);
        result = 31 * result + (dealerReceivingDatetime != null ? dealerReceivingDatetime.hashCode() : 0);
        result = 31 * result + (receiverDatetime != null ? receiverDatetime.hashCode() : 0);
        result = 31 * result + (receiverReceiving != null ? receiverReceiving.hashCode() : 0);
        result = 31 * result + (shipperWayBill != null ? shipperWayBill.hashCode() : 0);
        result = 31 * result + (recoveryId != null ? recoveryId.hashCode() : 0);
        result = 31 * result + (dimensions != null ? dimensions.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + shipmentOrderId;
        result = 31 * result + receiverId;
        result = 31 * result + dealerId;
        result = 31 * result + dispatchTripId;
        result = 31 * result + sarokhWarehouseId;
        result = 31 * result + zoneId;
        result = 31 * result + storageLocationsId;
        return result;
    }
}
