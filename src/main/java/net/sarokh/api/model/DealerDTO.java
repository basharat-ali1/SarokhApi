package net.sarokh.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class DealerDTO {
    private Integer id;
    private String companyName;
    private String companyNameAr;
    private String contact;
    private String crNumber;
    private String crFile;
    private String vatNumber;
    private String vatFile;
    private String address;
    private String city;
    private Integer zipCode;
    private String operationTiming;
    private String contractFile;
    private String operatorName;
    private String operatorContactNumber;
    private String operatorId;
    private String operatorDocumentFile;
    private String locationLatitude;
    private String locationLongitude;
    private String shopPicture;
    private String status;
    private int userId;
    private int bankAccountId;

}
