package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AssignCardToShipmentDTO {
    private String trackingNumber;
    private String cardNumber;
    private Boolean assignCard;
    private Integer warehouseId;
}
