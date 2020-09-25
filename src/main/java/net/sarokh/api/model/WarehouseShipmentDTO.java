package net.sarokh.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class WarehouseShipmentDTO {

    private Object shipmentTitle;
    private Object rowColumnRack;
    private Object deliveryDueDate;
    private Object receiverEmail;
    private Object deliveryZone;
    private String locationLatitude;
    private String locationLongitude;

    private Object trackingNumber;
    private String pickupType;
    private String deliveryType;
    private Object receiverName;
    private Object receiverContact;
    private Object shipper;
    private Object paymentType;
    private Object status;

}
