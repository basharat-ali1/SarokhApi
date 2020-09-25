package net.sarokh.api.model.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.enums.PickupTypeEnum;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderExcelFormDTO {

    private String orderId;
    private Integer shipperId;
    private String shipFromCity;
    private String shipToCity;
    private PickupTypeEnum pickupType;
    private String deliveryLocation;
    // Delivery location radio (lastmile and sarokhpoint)
    private String sarokhPointRadio;
    private Integer sarokhWarehouseId;
    private Integer shipperWarehouseId;

    private Integer dealerPointId;
    private String dealerPoint;

    private List<OrderShipmentItemDTO> shipmentItems;
}
