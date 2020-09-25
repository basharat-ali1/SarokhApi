package net.sarokh.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.order.ReceiverDTO;

import java.sql.Timestamp;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ShipmentDTO {
    private int id;
    private String shipmentTitle;
    private String shipmentContent;
    private Double shipmentValue;
    private String shipmentType;
    private String paymentType;
    private Double billedAmount;
    private Double shipmentCharges;
    private String dimensions;
    private Double weight;
    private ReceiverDTO receiver;




}
