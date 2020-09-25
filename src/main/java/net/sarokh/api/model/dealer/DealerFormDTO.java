package net.sarokh.api.model.dealer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.BasicInfoDTO;
import net.sarokh.api.model.LoginUser;

@Getter
@Setter
@Builder
public class DealerFormDTO {

    private Integer id;
    private String businessGroupName;
    private String owner;
    private String dateOfBirth;
    private String email;
    private String contactNo;
    private String address;
    private String city;
    private String country;
    private String profilePicture;
    private Integer postCode;

    private String contractStartDate;
    private String contractEndDate;
    private String contractFile;
    private String contractId;
    private String iqamaFile;
    private String nicNumber;

    private Integer bankAccountId;
    private String bank;
    private String iban;

    private Integer compensationCycle;
    private Double compensationPerShipment;

    private Integer userId;
    private String userName;
    private String password;

    private String locationLatitude;
    private String locationLongitude;

/*
    private BasicInfoDTO dealerBasicInfo;
    private DealerDetailsDTO dealerDetails;
    private DealerContractDTO dealerContract;
    private LoginUser credentials;*/
}
