package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class WalletsBalanceDTO {
    private Integer userTypeId;
    private String userType;
    private Double compensationWalletBalance;
    private Double collectionWalletBalance;
}
