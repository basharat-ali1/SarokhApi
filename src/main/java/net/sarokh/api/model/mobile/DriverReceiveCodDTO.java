package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DriverReceiveCodDTO {
    private Integer dealerId;
    private String dealerName;
    private String businessName;
    private double billedCOD;

}
