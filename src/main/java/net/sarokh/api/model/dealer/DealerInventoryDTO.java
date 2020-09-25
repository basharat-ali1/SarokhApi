package net.sarokh.api.model.dealer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DealerInventoryDTO {
    private Object orderId;
    private Object dateTime;
    private Object trackingNumber;
    private Object receiverName;
    private Object receiverContact;
    private Object status;
    private Object codAmount;
    private Object prepaidAmount;
}
