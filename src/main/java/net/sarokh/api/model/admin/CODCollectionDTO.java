package net.sarokh.api.model.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CODCollectionDTO {
    private Object ledgerId;
    private Object ledgerName;
    private Object currentBalance;
    private Object receivable;
    private Object payable;
    private Object billView;
    private Object ledgerHolder;
    private Object holderType;
    private Object notes;
}
