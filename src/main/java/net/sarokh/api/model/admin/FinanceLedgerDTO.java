package net.sarokh.api.model.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FinanceLedgerDTO {
    private Object transactionId;
    private Object transactionType;
    private Object dateTime;
    private Object from;
    private Object to;
    private Object amount;
    private Object details;
    private Object paymentMethod;
    private Object description;
    private Object billUpload;
}
