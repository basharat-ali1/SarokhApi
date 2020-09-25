package net.sarokh.api.model.shipper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShipperAddressDTO {

    private String address;
    private String city;
    private Integer postCode;
    private String country;
    private String locationLatitude;
    private String locationLongitude;
    private String concernedPerson;
    private String concernedPersonDesignation;
}
