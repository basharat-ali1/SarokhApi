package net.sarokh.api.model.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DealerPayoutDTO {
    private Object ledgerId;
    private Object dealerId;
    private Object timeDate;
    private Object ledgerName;
    private Object transactionType;
    private Object receivable;
    private Object payable;
    private Object billView;
    private Object paymentMethod;
    private Object description;
    private Object notes;
}
