package net.sarokh.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class ShipperTarrifDTO {
    private int id;
    private Double deliverySameCity;
    private Double deliveryDomesticT1;
    private Double deliveryDomesticT2;
    private Double deliveryDomesticT3;
    private Double insuranceCost;
    private Double cod;
    private Double lastMileCharges;
    private Double cancellationCharges;
    private Double returnCharges;
    private Double changeLocationCharges;
    private Timestamp createdDatetime;
    private Timestamp updatedDatetime;
    private int shipperShipperId;


}
