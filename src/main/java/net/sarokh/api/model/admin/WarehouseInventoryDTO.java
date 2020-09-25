package net.sarokh.api.model.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WarehouseInventoryDTO {
    private Object warehouseName;
    private Object timeDate;
    private Object usage;
    private Object totalInventory;
    private Object newDelivery;
    private Object reAttempt;
    private Object returnInv;

}
