package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class StopPointSearchDTO {
    private String stopType;
    private String stopTypeId;
    private Integer driverId;
}
