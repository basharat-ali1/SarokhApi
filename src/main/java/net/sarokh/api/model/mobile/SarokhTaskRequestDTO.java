package net.sarokh.api.model.mobile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder

public class SarokhTaskRequestDTO {
    private Integer taskId;
    private Integer receiveShipments;
    private Boolean allShipmentsReceived;
    private Integer giveShipments;
    private Boolean allShipmentsGiven;
    private Integer returnShipments;
    private Double payCOD;
    private Boolean isCODPaid;
    private Double pendingCOD;
    private String driverName;
    private String driverId;
    private Integer dealerId;
    private Integer report;
    private Integer tripId;

}
