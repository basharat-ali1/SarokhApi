package net.sarokh.api.model.shipper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.LoginUser;
import net.sarokh.api.model.BasicInfoDTO;
import net.sarokh.api.model.VerificationDTO;

@Getter
@Setter
@Builder
public class ShipperFormDTO {

    private BasicInfoDTO shipperBasicInfo;
    private String shipperType;
    private ShipperBusinessDetail shipperBusinessDetail;
    private ShipperWarehouseDTO shipperWarehouse;
    private ShipperAddressDTO billingAddress;
    private LoginUser security;
    private VerificationDTO verification;

}
