package net.sarokh.api.model.mobile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MobileCreateShipmentDTO {
    private int dealerId;
    private String senderName;
    private String senderContact;
    private String senderIdNumber;
    private String senderIdFile;
    private String senderAddress;
    private String receiverType;
    private String receiverName;
    private String receiverContact;
    private String receiverCity;
    private String receiverAddress;
    private String shipmentType;
    private String shipmentWeight;
    private String shipmentContent;
    private Double shipmentValue;
    private boolean normalPackaging;
    private boolean giftPackaging;
    private boolean insurance;
    private Double codAmount;
    private Double totalAmount;
    private Double deliveryCharges;
    private Double serviceCharges;
    private String deliveryType;
    private Integer deliverySarokhPointId;
    private String paymentMethod;
    private String locationLatitude;
    private String locationLongitude;

}
