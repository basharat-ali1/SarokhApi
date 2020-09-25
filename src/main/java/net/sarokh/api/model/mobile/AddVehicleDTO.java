package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddVehicleDTO {

    private String vehicleName;
    private String vehicleModel;
    private String make;
    private String type;
    private String cargoCapacity;
    private String registrationNumber;
    private String registrationFile;
    private Integer productionYear;
    private Integer registrationYear;
    private Integer currentMileage;
    private Integer driverId;
}
