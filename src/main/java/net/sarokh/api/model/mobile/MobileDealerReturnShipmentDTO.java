package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MobileDealerReturnShipmentDTO {

    private String trackingNo;
    private String shipmentNo;
    private String shipmentType;
    private String orderType;
    private String receiverName;
    private String contact;
    private String iqamaNumber;
    private String address;
    private String receiverPickupTime;
    private Double deliverCharges;
    private boolean shipmentReceived;


}
