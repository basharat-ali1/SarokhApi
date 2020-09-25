package net.sarokh.api.model.mobile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.enums.DriverPickAndDeliverEnum;
import net.sarokh.api.model.enums.PaymentTypeEnum;
import net.sarokh.api.model.enums.ReceiverStatusReportEnum;

import java.util.Date;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MobileDeliveryTripDTO {
    private Integer id;
    private String receiverName;
    // Dealer/Receiver
    private DriverPickAndDeliverEnum deliverTo;
    private String contactPerson;
    private String contactNumber;
    private String address;
    private String zone;
    private Date date;
    private String shipmentNumber;
    private String trackingNumber;
    private Boolean delivered;
    private String receivingCertificate;
    private Date receivingDateTime;
    private Double payment;
    private PaymentTypeEnum paymentType;
    private ReceiverStatusReportEnum customerReceivingReport;
    private String status;
}

