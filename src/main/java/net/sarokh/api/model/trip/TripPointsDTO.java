package net.sarokh.api.model.trip;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder

public class TripPointsDTO {
    private Integer pointId;
    private String dealerPointName;
    private String walletBalance;
    private String address;

}
