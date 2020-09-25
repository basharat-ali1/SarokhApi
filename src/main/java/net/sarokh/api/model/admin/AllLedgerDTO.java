package net.sarokh.api.model.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AllLedgerDTO {
    private Object ledgerId;
    private Object ledgerName;
    private Object ledgerHolder;
    private Object currentBalance;
    private Object receivable;
    private Object payable;
    private Object startDate;
    private Object endDate;

}
