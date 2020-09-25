package net.sarokh.api.model.shipper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.UserDTO;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ShipperWarehouseDTO {

    private Integer id;
    private String name;
    private String address;
    private String timing;
    private String city;
    private String country;
    private Integer postalCode;
    private String contact;
    private String email;
    private String concernedPerson;
    private String concernedPersonDesignation;
    private String workingHours;
    private String locationLongitude;
    private String locationLatitude;
    private List<UserDTO> users;

}
