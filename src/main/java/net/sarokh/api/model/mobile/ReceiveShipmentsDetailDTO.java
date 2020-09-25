package net.sarokh.api.model.mobile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ReceiveShipmentsDetailDTO {
    private String billNo;
    private String trackingNo;
    private String receiverName;
    private Double billedAmount;
    private String receivedStatus;
    private String giveStatus;
    private Object deliveryDate;
    private String type;
}
