package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AdminDashboardDTO {

    private Integer totalOrders;
    private Integer inProgressOrders;
    private Integer pendingDelivery;
    private Integer pendingPickups;
    private Double walletPickups;
    private Double codPayable;
    private Double prepaidOrdersReceivable;
    private Double agentsPayable;
    private Double agentsReceivable;
    private Double driverPayable;
    private Double driverReceivable;


}
