package net.sarokh.api.model.mobile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MobileReceivePickupDTO {
    private String trackingNumber;
    private Boolean received;
    private String receivingCertificate;
    private Date receivingDateTime;
    private String driverId;

}

