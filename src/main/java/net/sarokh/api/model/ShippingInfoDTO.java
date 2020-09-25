package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShippingInfoDTO {
    private int id;
    private String contactName;
    private String phone;
    private String email;
    private int shipperId;


}
