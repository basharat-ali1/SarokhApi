package net.sarokh.api.model.driver;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DriverDetailsDTO {
    private String address;
    private String city;
    private Integer postCode;
    private String country;
    private String locationLatitude;
    private String locationLongitude;
    // Passport/Iqama/NIC
    private String nicNumber;
    private String nicFile;

    private String licenceNumber;
    private String licenceFile;

}
