package net.sarokh.api.model.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.entity.DealerPoint;
import net.sarokh.api.model.entity.SarokhWarehouse;
import net.sarokh.api.model.entity.ShipperWarehouse;
import net.sarokh.api.model.enums.PickupTypeEnum;

import java.util.List;

@Getter
@Setter
@Builder
public class ShipmentPickupDeliveryDTO {

    private List<SarokhWarehouse> sarokhWarehouses;
    private Iterable<ShipperWarehouse> shipperWarehouses;
    private List<DealerPoint> sarokhPoints;
}
