package net.sarokh.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchShipmentDTO {
    private String startDate;
    private String endDate;
    private Integer shipperId;
}
