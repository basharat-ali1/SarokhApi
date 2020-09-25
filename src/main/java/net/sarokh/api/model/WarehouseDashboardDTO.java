package net.sarokh.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class WarehouseDashboardDTO {
    private String locationLatitude;
    private String locationLongitude;
    private Object totalShipments;
    private Object shipmentsAboutToReceive;
    private Object deliveriesDue;
    private List<WarehouseShipmentDTO> shipments;

}
