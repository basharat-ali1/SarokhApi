package net.sarokh.api.model.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.SarokhWarehouseDTO;
import net.sarokh.api.model.shipper.ShipperWarehouseDTO;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderFormDTO {



    private OrderBasicInfoDTO orderBasicInfo;
    private OrderShipperWarehouseInputDTO shipperWarehouse;
    private OrderSarokhWarehouseInputDTO sarokhWarehouse;
    private OrderDealerPointInputDTO dealerPoint;

    private List<OrderShipmentItemDTO> shipmentItems;
}
