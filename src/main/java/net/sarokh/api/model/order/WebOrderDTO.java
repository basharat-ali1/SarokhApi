package net.sarokh.api.model.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.enums.PickupTypeEnum;

import java.util.List;

@Getter
@Setter
@Builder
public class WebOrderDTO {

    private Integer id;
    private Integer shipperId;
    private String orderId;

    private String shipFromCity;
    private String shipToCity;

    private String pickupType;
    private String deliveryLocation;
    private String deliveryLocationRadio;

    private Integer sarokhWarehouseId;
    private Integer shipperWarehouseId;
    private Integer dealerPointId;

    private String receiverContact;
    private String receiverName;
    //private String sarokhPoint;

    private Double shipmentCost;
    private String shipmentTitle;
    private String shipmentType;
    private Double shipmentValue;
    private String shipmentWeight;
    private Double total;

    private boolean normalPackaging;
    private boolean giftPackaging;
    private boolean insurance;

    private Double codValue;
    private Double additionalCharges;
    private String billingType;
    private String content;
    private String address;
    private String locationLatitude;
    private String locationLongitude;

    private boolean update; // Flag for update order details

}
