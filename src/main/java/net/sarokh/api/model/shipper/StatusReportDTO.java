package net.sarokh.api.model.shipper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusReportDTO {
    private String paymentType;
    private String deliveryStatus;
    private Integer numberOfOrders;
}
