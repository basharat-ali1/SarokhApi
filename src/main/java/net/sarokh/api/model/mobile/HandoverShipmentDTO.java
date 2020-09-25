package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class HandoverShipmentDTO {
    private String mobileNumber;
    private String trackingNumber;
    private String receiverName;
    private String shipmentNo;
    private String contactNo;
    private String paymentType;
    private Double deliveryCharges;
    private String tentativePickupTime;
    private Double CODCharges;
    private Boolean delivered;
    private String receivedDateTime;
}
