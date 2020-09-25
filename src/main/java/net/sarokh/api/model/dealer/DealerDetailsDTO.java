package net.sarokh.api.model.dealer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DealerDetailsDTO {
    private String address;
    private String city;
    private Integer postCode;
    private String country;
    private String locationLatitude;
    private String locationLongitude;
    // Passport/Iqama/NIC
    private String nicNumber;
    private String nicFile;

    private String crNumber;
    private String crFile;

}
