package net.sarokh.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class DealerPointDTO {
    private Integer id;
    private String dealerPointName;
    private String commercialRegistrationNumber;
    private String commercialRegistrationFile;
    private String address;
    private String city;
    private String country;
    private Integer postalCode;
    private String operatorName;
    private String operatorContact;
    private String locationLatitude;
    private String locationLongitude;
    private String pointPicture;
    private String owner;
    private Integer ownerId;
    private String userName;
    private String password;
    private int userId;

}
