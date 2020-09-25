package net.sarokh.api.model.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class OrderShipmentItemDTO {
    private int id;
    private String trackingNumber;
    private String shipmentTitle;
    private String shipmentContent;
    private Double shipmentValue;
    private String shipmentType;
    private String paymentType;
    private Double billedAmount;
    private Double deliveryCharges;
    private String dimensions;
    private Double codAmount;
    private String weight;
    private String locationLatitude;
    private String locationLongitude;
    private String additionalServices;
    private Double additionalCharges;
    private boolean saveReceiver;

    private String receiverName;
    private String receiverMobileNumber;
    private String receiverAddress;
    private boolean update;
}
