package net.sarokh.api.model.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AllShipmentDTO {
    private Object id;
    private Object orderId;
    private Object dateTime;
    private Object pickType;
    private Object deliveryType;
    private Object receiverName;
    private Object status;

}
