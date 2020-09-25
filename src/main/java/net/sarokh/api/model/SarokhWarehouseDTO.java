package net.sarokh.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SarokhWarehouseDTO {
    private int id;
    private String name;
    private String address;
    private String city;
    private Integer postalCode;
    private String country;
    private String storageCapacity;
    private String dimensions;
    private String locationLatitude;
    private String locationLongitude;
    private List<UserDTO> users;
    private int warehouseManagerId;


}
