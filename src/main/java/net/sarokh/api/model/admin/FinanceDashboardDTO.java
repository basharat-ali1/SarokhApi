package net.sarokh.api.model.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.entity.Wallet;

@Getter
@Setter
@Builder
public class FinanceDashboardDTO {
    private Object monthlyReceivable;
    private Object monthlyPayable;
    private Object monthlyCodCollection;
    private Object currentBalance;
    private Object dueAmount;
    private Object totalExpense;
    private Iterable<Wallet> walletList;
}
