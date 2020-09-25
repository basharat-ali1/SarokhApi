package net.sarokh.api.model.driver;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeDriverDetailsDTO {
    private String compensationCycle;
    private Double compensation;
    private String contractStartDate;
    private String contactValidTill;
    private String contractFile;
    private Integer bankAccountId;
    private String bank;
    private String iban;

}
