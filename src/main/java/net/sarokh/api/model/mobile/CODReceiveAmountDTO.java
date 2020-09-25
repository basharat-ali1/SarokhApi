package net.sarokh.api.model.mobile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Builder
public class CODReceiveAmountDTO {

   private Integer driverId;
   private String driverSignature;
   private String dealerName;
   private Integer dealerID;
   private String dealerPointAddress;
   private Double receivedAmount;
   private Date dateTime;
}
