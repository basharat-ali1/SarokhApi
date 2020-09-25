package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class HandoverReceivedShipmentDTO {

    private String receiverName;
    private String shipmentNo;
    private Double CODCharges;
    private String delivererName;
    private String signature;
    private String receivedDateTime;
}
