package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MobileShipmentDTO {

    private String trackingNo;
    private String shipmentNo;
    private String shipmentType;
    private String mobileNo;
    private String orderType;
    private String receiver;
    private String receiverPickupTime;
    private String deliveryTime;
    private Double shipmentBill;
    private Double deliverCharges;
    private boolean shipmentReceived;
}
