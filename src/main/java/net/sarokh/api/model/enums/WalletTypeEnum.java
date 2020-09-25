package net.sarokh.api.model.enums;

public enum WalletTypeEnum {
    DealerCompensation("Dealer Compensation"), DealerCollection("Dealer Collection"),
    DriverCompensation("Driver Compensation"), DriverCollection("Driver Collection"),
    SarokhWallet("Sarokh Wallet"), ShipperCod("Shipper COD"), ShipperDeliveryCharges("ShipperDeliveryCharges");

    private final String walletType;

    WalletTypeEnum(String walletType) {
        this.walletType = walletType;
    }

    public String getWalletType() {
        return walletType;
    }
}
