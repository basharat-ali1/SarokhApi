package net.sarokh.api.model.enums;

public enum AdditionalServicesEnum {
    NormalPackaging("Normal Packaging"), GiftPackaging("Gift Packaging"), Insurance("Insurance");

    private final String serviceName;

    AdditionalServicesEnum(String name){
        this.serviceName = name;
    }

    public String getServiceName(){
        return serviceName;
    }
}
