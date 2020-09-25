package net.sarokh.api.model.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CODShipmentsDTO {
    private Object id;
    private Object orderId;
    private Object dateTime;
    private Object receiverName;
    private Object codAmount;
    private Object status;
    private Object shipFromCity;
    private Object shipToCity;
}
