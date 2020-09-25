package net.sarokh.api.model.mobile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.enums.DriverPickAndDeliverEnum;

import java.util.Date;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MobilePickupTripDTO {
    private Integer id;
    private String pickupName;
    // Dealer/Shipper
    private DriverPickAndDeliverEnum pickupFrom;
    private String contactPerson;
    private String contactNumber;
    private String address;
    private String zone;
    private Date date;
    private Boolean forkLifterRequired;
    private String shipmentNumber;
    private String trackingNumber;
    private String cargoType;
    private Boolean received;
    private String receivingCertificate;
    private Date receivingDateTime;
    private String status;
}

