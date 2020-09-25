package net.sarokh.api.model.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShipperBillingDTO {
    private Object shipperId;
    private Object shipperName;
    private Object date;
    private Object period;
    private Object amount;
    private Object status;
    private Object ledger;

}
