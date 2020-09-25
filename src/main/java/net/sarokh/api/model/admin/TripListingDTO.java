package net.sarokh.api.model.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TripListingDTO {
    private Object tripId;
    private Object date;
    private Object sarkhWarehouse;
    private Object driver;
    private Object driverType;
    private Object shipmentPickup;
    private Object shipmentDelivered;
    private Object CODCollection;
    private Object status;
}
