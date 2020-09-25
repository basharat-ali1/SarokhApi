package net.sarokh.api.model.shipper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.BasicInfoDTO;
import net.sarokh.api.model.LoginUser;
import net.sarokh.api.model.VerificationDTO;
import net.sarokh.api.model.entity.DeliveryCharges;

@Getter
@Setter
@Builder
public class ShipperDeliveryChargesDTO {

    private Integer shipperId;
    private DeliveryCharges pointDelivery;
    private DeliveryCharges lastMileDelivery;

}
