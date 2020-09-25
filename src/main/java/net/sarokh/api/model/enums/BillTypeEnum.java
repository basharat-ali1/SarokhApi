package net.sarokh.api.model.enums;

public enum BillTypeEnum {
    Invoice("Invoice"), CreditNote("Credit Note"),
    Debit("Debit"), Credit("Credit");

    private final String typeName;

    BillTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
