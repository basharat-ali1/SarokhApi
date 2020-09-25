package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CODRecoveryDTO {
    Integer dealerId;
    String dealerName;
    String zone;
    Double collectionAmount;
    String address;
    String contactPerson;
    String contact;
}
