package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class WalletDealerDTO {
    private Double payToSarokhBalance;
    private Double sarokhPay;
    private Double codCollected;
}
