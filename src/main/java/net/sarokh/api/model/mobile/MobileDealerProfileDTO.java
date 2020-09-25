package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MobileDealerProfileDTO {

    private String dealerName;
    private String businessName;
    private String contactNoOwner;
    private String contactNoBusiness;
    private String businessEmail;
    private String businessTiming;


}
