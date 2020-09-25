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
public class SarokhTaskConfirmDTO {
    private Boolean receiveShipments;
    private Boolean giveShipments;
    private Boolean payCOD;
    private Boolean pendingCOD;
    private Boolean reportIssues;
    private String driverId;
    private String driverName;
    private String confirmationId;
}
