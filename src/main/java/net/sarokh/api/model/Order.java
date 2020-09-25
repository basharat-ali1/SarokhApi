package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Order {

    private Integer orderId;
    private Integer shipperId;
    private String orderType;
    private String shipmentClassifcation;
    private String pickupTariff;
    private Integer shipperWarehouseID;
    private Integer userID;
    private String pickupTime;
    private String receivingtime;
    private String transitTime;
    private String receiving;
    private String receivingLocation;
    private Double totalWorth;
    private String receivingStatus;
}
