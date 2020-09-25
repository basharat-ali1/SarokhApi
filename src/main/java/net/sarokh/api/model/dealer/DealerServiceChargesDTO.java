package net.sarokh.api.model.dealer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DealerServiceChargesDTO {
    private Object dealerId;
    private Object ledgerId;
    private Object currentBalance;
    private Object receivable;
    private Object payable;
    private Object dueAmount;
}
