package net.sarokh.api.model.driver;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FreelanceDriverVehicleDTO {
    private Integer vehicleId;
    private String name;
    private String model;
    private String make;
    private String type;
    private String cargoCapacity;
    private String registrationNumber;
    private String registrationFile;
    private Integer productionYear;
    private Integer registrationYear;
}
