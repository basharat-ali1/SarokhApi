package net.sarokh.api.model.enums;

public enum PickupTypeEnum {
    SarokhWarehouse("Sarokh Warehouse"), DealerPoint("Sarokh Point"), ShipperWarehouse("Shipper Warehouse");

    private final String pickupTypeName;

    PickupTypeEnum(String pickupType) {
        this.pickupTypeName = pickupType;
    }

    public String getPickupTypeName() {
        return pickupTypeName;
    }
}
