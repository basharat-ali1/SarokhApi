package net.sarokh.api.model.mobile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DriverSarokhTaskDTO {
    private Integer id;
    private String stopType;
    private Integer stopTypeId;
    private String receiverType;
    private String receiverName;
    private String latitude;
    private String longitude;
    private boolean delivered;
    private String locationName;
    private Integer dealerId;

    private boolean allShipmentsGiven;
    private boolean allShipmentsReceived;
    private boolean isCODPaid;
    private Integer tripId;
    private Integer receiveShipments;
    private Integer giveShipments;
    private Double payCOD;
}
