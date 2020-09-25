package net.sarokh.api.model.trip;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AssignDriverToShipmentDTO {
    private String trackingNumber;
    private Integer driverId;
    private Boolean assignDriver;
    
}
