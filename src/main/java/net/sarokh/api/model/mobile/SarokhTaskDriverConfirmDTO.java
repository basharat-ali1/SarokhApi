package net.sarokh.api.model.mobile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SarokhTaskDriverConfirmDTO {
    private Boolean receiveShipments;
    private Boolean giveShipments;
    private Boolean payCOD;
    private Boolean pendingCOD;
    private Boolean reportIssues;
    private Integer driverId;
    private String driverName;
    private Integer confirmationId;
    private String signature;
}
