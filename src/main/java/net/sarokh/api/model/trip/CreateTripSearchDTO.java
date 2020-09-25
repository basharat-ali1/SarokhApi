package net.sarokh.api.model.trip;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CreateTripSearchDTO {
    private Integer warehouseId;
    private Integer vehicleId;
    private Integer driverId;
}
