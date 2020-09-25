package net.sarokh.api.model.trip;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class CreateTripDetailsDTO {
    private Integer totalCargoCapacity;
    private Double CODCollection;
    private String shipmentDisplaced;
    private Integer warehouseId;
    private Integer driverId;
    private Integer vehicleId;
    private List<TripDetailsDTO> shipmentsList;
    private List<TripPointsDTO> pointsList;
}
