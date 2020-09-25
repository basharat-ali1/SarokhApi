package net.sarokh.api.model.mobile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class SarokhTaskDTO {
    private List<String> receiveShipments;
    private List<String> giveShipments;
    private Double payCOD;
    private Double pendingCOD;
    private String driverName;
    private String driverId;
    private List<String> report;
    private String signature;
}
