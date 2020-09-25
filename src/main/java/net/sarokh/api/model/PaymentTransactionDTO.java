package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class PaymentTransactionDTO {
    private int id;
    private String transactionType;
    private String description;
    private String from;
    private String to;
    private Double amount;
    private String paymentMethod;
    private String bill;
    private String datetime;
    private int walletId;

}
