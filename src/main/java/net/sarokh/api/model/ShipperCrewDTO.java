package net.sarokh.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipperCrewDTO {
    private int id;
    private int shipperId;
    private int userId;

}
