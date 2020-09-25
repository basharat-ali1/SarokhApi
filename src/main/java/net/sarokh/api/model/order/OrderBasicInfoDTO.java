package net.sarokh.api.model.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.enums.PickupTypeEnum;
import net.sarokh.api.model.enums.ShipmentTypeEnum;

@Getter
@Setter
@Builder
public class OrderBasicInfoDTO {

    private String orderId;
    private Integer shipperId;
    private String shipFromCity;
    private String shipToCity;
    private PickupTypeEnum pickupType;
    private String deliveryLocation;


}
