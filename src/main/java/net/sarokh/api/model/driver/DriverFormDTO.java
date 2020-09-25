package net.sarokh.api.model.driver;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.BasicInfoDTO;
import net.sarokh.api.model.LoginUser;

@Getter
@Setter
@Builder
public class DriverFormDTO {

    private Integer id;
    private String driverType;
    private String profilePicture;
    private String firstName;
    private String lastName;
    private String contact;
    private String email;
    private String dateOfBirth;
    private String nicFile;
    private String licenseFile;
    private String address;
    private String country;
    private String city;
    private Integer postCode;
    private String nicNumber;
    private String licenseNumber;
    private String registrationFile;
    private Integer vehicleId;
    private String vehicleName;
    private String vehicleModel;
    private String make;
    private String type;
    private String cargoCapacity;
    private String registrationNumber;
    private Integer productionYear;
    private Integer registrationYear;
    private String contractFile;
    private String compensationCycle;
    private Double compensation;
    private String contractStartDate;
    private String contactValidTill;
    private Integer bankAccountId;
    private String bank;
    private String iban;
    private Integer userId;
    private String userName;
    private String password;
    private boolean ready;
    private Integer warehouseId;

    /*
    Old format

    private String driverType;
    private BasicInfoDTO basicInfo;
    private DriverDetailsDTO driverDetails;
    private FreelanceDriverVehicleDTO freelanceDriverVehicle;
    private EmployeeDriverDetailsDTO employeeDriverDetails;
    private LoginUser credentials;*/

}
