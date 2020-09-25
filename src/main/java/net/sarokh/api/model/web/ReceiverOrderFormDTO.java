package net.sarokh.api.model.web;

import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.entity.DealerPoint;

import java.util.List;

@Getter
@Setter
public class ReceiverOrderFormDTO {
    private String trackingNumber;
    private String deliveryLocation;
    private String address;
    private String date;
    private Integer dealerPointId;
}
