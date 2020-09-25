package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class VehicleMaintenanceDTO {
    private int id;
    private String description;
    private String maintenanceType;
    private String repairFacility;
    private String repairFacilityContact;
    private String authorizedSignature;
    private Timestamp repairStartDatetime;
    private Timestamp repairEndDatetime;
    private String invoice;
    private int vehicleId;

}
