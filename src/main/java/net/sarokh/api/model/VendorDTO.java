package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class VendorDTO {
    private Integer id;
    private String companyName;
    private String contact;
    private String address;
    private String city;
    private String country;
    private String email;
    private String commercialRegistrationNumber;
    private String crFile;
    private String paymentApiId;
    private String paymentApiKey;
    private String paymentApiUrl;
    private String bankName;
    private String bankAccountIban;
    private String userName;
    private String password;
    private Integer userId;
    private Integer bankAccountId;
}
