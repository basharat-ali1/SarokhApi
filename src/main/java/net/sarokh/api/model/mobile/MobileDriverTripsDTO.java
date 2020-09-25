package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MobileDriverTripsDTO {
    private Integer tripId;
    private String date;
    private String startTime;
    private String endTime;
    private Integer totalPickups;
    private Integer totalDeliveries;
    private Integer totalStops;
    private Double paymentCollection;

}
