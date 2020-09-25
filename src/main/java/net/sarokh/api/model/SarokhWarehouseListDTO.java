package net.sarokh.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.entity.SarokhWarehouse;
import net.sarokh.api.model.entity.ShipperWarehouse;

import java.util.List;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SarokhWarehouseListDTO {
    private Iterable<SarokhWarehouse> warehouseList;
    private List<MapLocationDTO> mapLocations;
}
