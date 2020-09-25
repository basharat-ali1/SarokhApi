package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RecordPaymentDTO {
    private Integer billNo;
    private String paymentMethod;
    private Double amountPaid;
    private String paymentNote;
    private String paymentDate;
    private Integer userId;
    private Integer walletId;
    private String paymentType;
}
