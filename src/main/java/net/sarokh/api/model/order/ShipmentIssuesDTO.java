package net.sarokh.api.model.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShipmentIssuesDTO {
    private Object id;
    private Object orderId;
    private Object shipmentId;
    private Object reportedBy;
    private Object issueType;
    private Object notes;
    private Object shipFromCity;
    private Object shipToCity;
}
