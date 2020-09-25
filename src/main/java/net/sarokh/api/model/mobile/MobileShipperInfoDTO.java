package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MobileShipperInfoDTO {

    private String name;
    private Integer shipperId;
}
