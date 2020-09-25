package net.sarokh.api.model.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderShipperWarehouseInputDTO {
    private String warehouseId;
    private String dateTime;
    private String concernPersonId;

}
