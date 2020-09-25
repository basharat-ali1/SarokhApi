package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WarehouseStorageDTO {
    private int id;
    private String name;
    private Integer columnNumber;
    private Integer rowNumber;
    private String size;
    private Byte occupied;
    private String details;
    private int sarokhWarehouseId;

}
