package net.sarokh.api.model.shipper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShipperBusinessDetail {

    private String businessName;
    private String businessLogo;
    private String iqamaFile;
    private String iqamaNumber;
    private String vatNumber;
    private String crNumber;
    private String crFile;
    private String bankName;
    private String iban;
}
