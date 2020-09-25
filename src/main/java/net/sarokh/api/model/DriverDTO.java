package net.sarokh.api.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DriverDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String idType;
    private String idNumber;
    private String idFile;
    private String address;
    private String city;
    private Integer postalCode;
    private String country;
    private String contractFile;
    private String driverType;
    private String salary;
    private String status;
    private Integer performanceRating;
    private String notes;
    private int userId;

}
