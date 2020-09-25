package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ShipperDashboardDTO {
/*
    private Integer totalEarning;
    private Integer completedOrders;

    private Double pendingCOD;
    private Double codPayable;
    private Integer totalOrders;
    private Integer pendingOrders;

    private Integer returnOrders;
    private Integer ordersPendingDeliveries;
    private Integer activeShipmentIssues;
    */

    private Object totalEarning;
    private Object completedOrders;

    private Object pendingCOD;
    private Object pendingDeliveryCharges;
    private Object totalOrders;
    private Object pendingOrders;

    private Object returnOrders;
    private Object ordersPendingDeliveries;
    private Object activeShipmentIssues;

    private List<String> usersList;
}
