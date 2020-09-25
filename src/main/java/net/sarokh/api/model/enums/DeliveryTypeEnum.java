package net.sarokh.api.model.enums;

public enum DeliveryTypeEnum {
    SarokhPoint("To Sarokh Point"), PredefinedLocation("To Predefined Location"),
        LastMile("Last Mile"), ReceiverAddress("Receiver Address");

    private final String deliveryTypeName;

    DeliveryTypeEnum(String deliveryTypeName) {
        this.deliveryTypeName = deliveryTypeName;
    }

    public String getDeliveryTypeName() {
        return deliveryTypeName;
    }
}
