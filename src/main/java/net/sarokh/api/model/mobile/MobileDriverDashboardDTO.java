package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MobileDriverDashboardDTO {
    private Integer droppedOff;
    private Integer pickedUp;
    private Integer availableShipments;
    private Double collectionWalletBalance;
    private Double compensationWalletBalance;
    private Integer completedStops;
    private Integer remainingStops;
    private Integer trips;
    private String cargoCapacity;
    private String vehicle;
    private Double nextStopDistance;

}
