package net.sarokh.api.model.order;

public enum ShippingOrderType {
    PickupBySarokh("Pickup By Sarokh"), DeliveryToSarokhWH("Delivery To Sarokh Warehouse"),
        DeliveryToSarokhPoint("Delivery To Sarokh Point"), DeliveryToCustomer("Delivery To Customer");

    private final String name;

    ShippingOrderType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
