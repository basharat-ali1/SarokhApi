package net.sarokh.api.model.shipper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.MapLocationDTO;
import net.sarokh.api.model.entity.ShipperWarehouse;

import java.util.List;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ShipperWarehouseListDTO {
    private Iterable<ShipperWarehouse> warehouseList;
    private List<MapLocationDTO> mapLocations;
}
