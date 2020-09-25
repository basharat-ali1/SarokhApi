package net.sarokh.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchBulkShipmentDTO {
    private String startTrackingNumber;
    private String endTrackingNumber;
}
