package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sarokh.api.model.DriverDTO;


import java.util.List;

@Setter
@Getter
@Builder
public class CashReceiverAgentsDTO {

    private Double cashInWallet;
    private List<CashCollectionAgent> collectionAgentsList;
    private List<DriverDTO> driversList;

}
