package net.sarokh.api.model.dealer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DealerContractDTO {
    private Integer compensationClearanceDuration;
    private Double preShipmentCompensation;
    private String contractStartDate;
    private String contractValidToDate;
    private String contractFile;
    private Integer bankAccountId;
    private String bankName;
    private String iban;


}
