package net.sarokh.api.model.mobile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MobileShippersListDTO {
    private Integer shipperId;
    private String shipperName;
    private List<String> shipmentList;
    private String signature;
    private Integer dealerId;
    private Integer driverId;
}
