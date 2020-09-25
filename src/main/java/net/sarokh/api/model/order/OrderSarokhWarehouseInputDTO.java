package net.sarokh.api.model.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.UserDTO;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderSarokhWarehouseInputDTO {
    private String warehouseId;
    private String dateTime;
    private String concernPersonId;
    private String contact;
}
