package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MobileDealerDashboardDTO {

    private Integer availableShipments;
    private Integer walletBalance;
    private Integer shipmentsIn;
    private Integer shipmentsOut;
    private Double cashIn;
    private Double cashOut;

}
