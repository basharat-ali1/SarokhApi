package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WarehouseTerminalDTO {

    private String trackingNumber;
    private String fromCity;
    private String toCity;
    private String shipper;
    private String address;

}
