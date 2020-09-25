package net.sarokh.api.model.order;

import io.swagger.models.auth.In;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.enums.PickupTypeEnum;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderNewFormDTO {
    private Integer id;
    private String orderId;
    private Integer shipperId;
    private String shipFromCity;
    private String shipToCity;
    private String pickupType;
    private String deliveryLocation;
    // Delivery location radio (lastmile and sarokhpoint)
    private String deliveryLocationRadio;
    private Integer sarokhWarehouseId;
    private Integer shipperWarehouseId;

    private Integer dealerPointId;
    private String dealerPoint;
    private String orderFile;
    private boolean update;

    private List<OrderShipmentItemDTO> shipmentItems;
}
