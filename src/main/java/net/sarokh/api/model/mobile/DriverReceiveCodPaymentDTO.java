package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DriverReceiveCodPaymentDTO {
    private Integer dealerId;
    private Integer driverId;
    private Double codAmount;

}
