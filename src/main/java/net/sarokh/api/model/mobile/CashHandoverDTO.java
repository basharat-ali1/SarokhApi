package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CashHandoverDTO {
    private Integer dealerId;
    private String receiverName;
    private Integer driverId;
    private Integer agentId;
    private String paymentDate;
    private Double amount;
    private String signature;
}
