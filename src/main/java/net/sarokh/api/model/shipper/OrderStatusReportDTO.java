package net.sarokh.api.model.shipper;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderStatusReportDTO {
    //private String deliveryStatus;
    private int codOrders;
    private int prepaidOrders;
    private int fullPrepaidOrders;
}
