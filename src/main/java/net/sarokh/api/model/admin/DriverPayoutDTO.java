package net.sarokh.api.model.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DriverPayoutDTO {
    private Object ledgerId;
    private Object driverId;
    private Object timeDate;
    private Object ledgerName;
    private Object transactionType;
    private Object driverType;
    private Object receivable;
    private Object payable;
    private Object billView;
    private Object paymentMethod;
    private Object description;
    private Object notes;
}
