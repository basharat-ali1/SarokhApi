package net.sarokh.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ShipmentOrderDTO {
    private int id;
    private String trackingNo;
    private String mobile;
    private String shipmentClassification;
    private Double shipmentPrice;
    private Double billedAmount;
    private String pickupDatetime;
    private String pickupBy;
    private String driverArrivalTime;
    private String transitTime;
    private String receiverName;
    private String receiverType;
    private String status;
    private String orderType;
    private Double deliveryCharges;
    private String shipmentNo;
    private String createdDatetime;
    private String updatedDatetime;
    private int shipperWarehouseId;
    private int shipperId;
    private Boolean shipmentReceived;
    private int orderPickupTypeId;

}
