package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReceiveShipmentSearchDTO {
    private String trackingNumber;
    private String receivedFrom;
    private String returnFrom;
}
