package net.sarokh.api.model.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDropOffDTO {

    private String city;
    private Integer zone;
    private String point;
    private String time;
    private String date;
    private String deliveryPersonName;
    private String contactNumber;

}
