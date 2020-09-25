package net.sarokh.api.model.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PendingShipmentsDTO {

    private Object id;
    private Object orderId;
    private Object dateTime;
    private Object status;
    private Object receiverName;
    private Object paymentType;
    private Object codAmount;
    private Object shipFromCity;
    private Object shipToCity;
    private Object shipmentTitle;

}
