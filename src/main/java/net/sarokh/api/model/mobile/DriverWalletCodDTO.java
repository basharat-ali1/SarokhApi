package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DriverWalletCodDTO {
    private Integer driverId;
    private Double codAmount;
}
