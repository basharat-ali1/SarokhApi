package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class BillSummaryDTO {
    private Integer units;
    private String trackingNumber;
    private String date;
    private Double amount;
}
