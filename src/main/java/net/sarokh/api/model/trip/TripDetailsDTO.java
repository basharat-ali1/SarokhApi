package net.sarokh.api.model.trip;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder

public class TripDetailsDTO {
    private Integer warehouseId;
    private String shipmentId;
    private String locationName;
    private String pickupType;
    private String deliveryType;
    private String address;
    private String deliveryPickup;
    private Double CODCollection;
    private Integer weight;
    private String shipper;

}
