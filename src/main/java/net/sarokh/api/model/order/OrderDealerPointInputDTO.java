package net.sarokh.api.model.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDealerPointInputDTO {
    private String id;
    private String dateTime;
    private String concernPersonId;
    private String dealerPoint;
}
