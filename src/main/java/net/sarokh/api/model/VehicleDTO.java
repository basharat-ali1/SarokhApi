package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class VehicleDTO {
    private int id;
    private String name;
    private String owner;
    private String registrationNumber;
    private String registrationFile;
    private String model;
    private String make;
    private Integer year;
    private String type;
    private String status;
    private String cargoCapacity;
    private String currentMilage;
    private Timestamp createdDate;

}
