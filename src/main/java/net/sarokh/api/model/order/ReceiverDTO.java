package net.sarokh.api.model.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ReceiverDTO {
    private int id;
    private String customerName;
    private String receiverName;
    private String contact;
    private String email;
    private String address;
    private String city;
    private String country;
    private Integer postalCode;
    private String locationLongitude;
    private String locationLatitude;

}
