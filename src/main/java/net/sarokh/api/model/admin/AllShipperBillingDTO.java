package net.sarokh.api.model.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AllShipperBillingDTO {
    private Object id;
    private Object shipperName;
    private Object transactionType;
    private Object numberOfShipments;
    private Object amount;
    private Object date;

}
