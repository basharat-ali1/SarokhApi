package net.sarokh.api.model.enums;

public enum BillCategoryEnum {
    ShipmentCharges("Shipment Charges"), COD("COD"), Compensation("Compensation"),
    Other("Other"), ShipperCod("Shipper COD"), ShipperDeliveryCharges("Shipper Delivery Charges"),
        DriverPayout("Driver Payout"), DealerPayout("Dealer Payout");

    private final String billCateogry;

    BillCategoryEnum(String billCateogry) {
        this.billCateogry = billCateogry;
    }

    public String getBillCateogry() {
        return billCateogry;
    }
}
