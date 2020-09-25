package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DriveReceiveAmountDTO {
    private Integer driverId;
    private Integer taskId;
    private Double amount;
}
