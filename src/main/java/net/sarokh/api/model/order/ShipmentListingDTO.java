package net.sarokh.api.model.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class ShipmentListingDTO {
    private Object id;
    private Object shipmentId;
    private Object dateTime;
    private Object fromCity;
    private Object toCity;
    private Object status;
    private Object shipper;
    private Object currentLocation;
    private Object assignToDriver;
    private Object paymentType;
    private Object pickupLocation;
    private Object codAmount;
    private Object deliveryType;
}
