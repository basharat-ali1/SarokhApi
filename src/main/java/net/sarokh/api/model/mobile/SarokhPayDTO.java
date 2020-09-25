package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SarokhPayDTO {
    private String biller;
    private String billNo;
    private Double paidAmount;
}
