package net.sarokh.api.model.mobile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MobileCODLedgerDTO {
    private Object ledgerId;
    private Object deliveryDate;
    private Object amountPaid;
    private Object pendingAmount;
    private Object driverId;
    private Object driverName;
}
